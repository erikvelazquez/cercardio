package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.PacientMedicalAnalysis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PacientMedicalAnalysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacientMedicalAnalysisRepository extends JpaRepository<PacientMedicalAnalysis, Long> {

}
