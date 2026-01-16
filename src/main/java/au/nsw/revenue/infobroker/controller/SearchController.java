package au.nsw.revenue.infobroker.controller;

import au.nsw.revenue.infobroker.dto.SearchRequestDto;
import au.nsw.revenue.infobroker.dto.SearchResponseDto;
import au.nsw.revenue.infobroker.model.SearchHistory;
import au.nsw.revenue.infobroker.model.User;
import au.nsw.revenue.infobroker.service.AuthService;
import au.nsw.revenue.infobroker.service.SearchService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/search")
@CrossOrigin(origins = "*")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private AuthService authService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<SearchResponseDto> performSearch(
            @RequestBody SearchRequestDto request,
            HttpServletRequest httpRequest) {

        User currentUser = authService.getCurrentUser();
        String ipAddress = getClientIpAddress(httpRequest);

        SearchResponseDto response = searchService.performSearch(request, currentUser, ipAddress);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/history")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public ResponseEntity<List<SearchHistory>> getSearchHistory() {
        User currentUser = authService.getCurrentUser();
        List<SearchHistory> history = searchService.getUserSearchHistory(currentUser);
        return ResponseEntity.ok(history);
    }

    private String getClientIpAddress(HttpServletRequest request) {
        String xForwardedFor = request.getHeader("X-Forwarded-For");
        if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
            return xForwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
