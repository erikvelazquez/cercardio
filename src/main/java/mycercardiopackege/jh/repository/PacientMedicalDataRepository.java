package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.PacientMedicalData;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PacientMedicalData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacientMedicalDataRepository extends JpaRepository<PacientMedicalData, Long> {

}
