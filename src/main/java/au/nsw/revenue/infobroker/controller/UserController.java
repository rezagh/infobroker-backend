package au.nsw.revenue.infobroker.controller;

import au.nsw.revenue.infobroker.model.User;
import au.nsw.revenue.infobroker.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private AuthService authService;

    @GetMapping("/me")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<Map<String, Object>> getCurrentUser() {
        User user = authService.getCurrentUser();

        Map<String, Object> response = new HashMap<>();
        response.put("id", user.getId());
        response.put("email", user.getEmail());
        response.put("firstName", user.getFirstName());
        response.put("lastName", user.getLastName());
        response.put("roles", user.getRoles());
        response.put("mfaEnabled", user.getMfaEnabled());
        response.put("active", user.getActive());

        return ResponseEntity.ok(response);
    }
}
