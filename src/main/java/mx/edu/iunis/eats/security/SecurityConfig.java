package mx.edu.iunis.eats.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Agregamos /manifest.json y /sw.js para que Chrome pueda instalar la PWA
                        .requestMatchers("/css/**", "/images/**", "/manifest.json", "/sw.js", "/register", "/login").permitAll()

                        // Usamos hasAuthority para que coincida con tu base de datos
                        .requestMatchers("/admin/**").hasAuthority("CAFETERIA_STAFF")
                        .requestMatchers("/users/**", "/user/**").hasAuthority("CAFETERIA_STAFF")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .permitAll()
                );

        return http.build();
    }
}