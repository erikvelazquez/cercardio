package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.SocialHealthInsurance;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SocialHealthInsurance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocialHealthInsuranceRepository extends JpaRepository<SocialHealthInsurance, Long> {

}
