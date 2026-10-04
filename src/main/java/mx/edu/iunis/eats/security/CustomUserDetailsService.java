package mx.edu.iunis.eats.security;

import mx.edu.iunis.eats.domain.User;
import mx.edu.iunis.eats.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByUserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPassword())
                .roles(getRoleName(user.getRole()))
                .build();
    }

    private String getRoleName(Integer role) {
        if (role == null) {
            throw new IllegalArgumentException("El usuario no tiene un rol");
        }

        return switch (role) {
            case 1 -> "STUDENT";
            case 2 -> "CAFETERIA_STAFF";
            default -> throw new IllegalArgumentException(
                    "Rol no válido: " + role
            );
        };
    }
}