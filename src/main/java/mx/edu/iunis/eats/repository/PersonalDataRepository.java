package mx.edu.iunis.eats.repository;

import mx.edu.iunis.eats.domain.PersonalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalDataRepository extends JpaRepository<PersonalData, Long> {
    void deleteAllByUserId(Long userId);
}
