package mx.edu.iunis.eats.repository;

import mx.edu.iunis.eats.domain.Platillo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlatilloRepository extends JpaRepository<Platillo, Long> {
    List<Platillo> findByDisponibilidadTrue();
    List<Platillo> findByCategoria(String categoria);
}