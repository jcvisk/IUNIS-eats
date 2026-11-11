package mx.edu.iunis.eats.repository;

import mx.edu.iunis.eats.domain.ComboPlatillo;
import mx.edu.iunis.eats.domain.ComboPlatilloId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComboPlatilloRepository
        extends JpaRepository<ComboPlatillo, ComboPlatilloId> {

    List<ComboPlatillo> findByIdCombo(Long idCombo);

}