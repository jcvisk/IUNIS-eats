package mx.edu.iunis.eats.config;

import mx.edu.iunis.eats.domain.User;
import mx.edu.iunis.eats.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {

        String userName = "cafeteria";

        if (userRepository.findByUserName(userName).isEmpty()) {

            User cafeteriaStaff = new User();

            cafeteriaStaff.setUserName(userName);
            cafeteriaStaff.setPassword(
                    passwordEncoder.encode("cafeteria123")
            );
            cafeteriaStaff.setRole(2); // CAFETERIA_STAFF

            userRepository.save(cafeteriaStaff);
        }
    }
}
