package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.MedicalAnalysis;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the MedicalAnalysis entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicalAnalysisRepository extends JpaRepository<MedicalAnalysis, Long> {

}
