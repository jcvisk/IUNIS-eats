package mx.edu.iunis.eats.repository;

import mx.edu.iunis.eats.domain.DatosPersonales;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DatosPersonalesRepository extends JpaRepository<DatosPersonales, Long> {
    void deleteAllByUserId(Long userId);
}
