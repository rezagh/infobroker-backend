package au.nsw.revenue.infobroker.config;

import au.nsw.revenue.infobroker.model.User;
import au.nsw.revenue.infobroker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import java.util.Set;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Create demo admin user if not exists
        if (!userRepository.existsByEmail("admin@revenue.nsw.gov.au")) {
            User admin = new User();
            admin.setEmail("admin@revenue.nsw.gov.au");
            admin.setPassword(passwordEncoder.encode("Admin123!"));
            admin.setFirstName("Admin");
            admin.setLastName("User");
            admin.setActive(true);
            admin.setMfaEnabled(false);
            admin.setRoles(Set.of("ADMIN", "USER"));
            userRepository.save(admin);

            System.out.println("✅ Demo admin user created: admin@revenue.nsw.gov.au / Admin123!");
        }

        // Create demo regular user if not exists
        if (!userRepository.existsByEmail("user@revenue.nsw.gov.au")) {
            User user = new User();
            user.setEmail("user@revenue.nsw.gov.au");
            user.setPassword(passwordEncoder.encode("User123!"));
            user.setFirstName("John");
            user.setLastName("Smith");
            user.setActive(true);
            user.setMfaEnabled(false);
            user.setRoles(Set.of("USER"));
            userRepository.save(user);

            System.out.println("✅ Demo regular user created: user@revenue.nsw.gov.au / User123!");
        }
    }
}
