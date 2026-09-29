package mx.edu.iunis.eats.repository;

import mx.edu.iunis.eats.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
