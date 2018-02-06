package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.PrivateHealthInsurance;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PrivateHealthInsurance entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrivateHealthInsuranceRepository extends JpaRepository<PrivateHealthInsurance, Long> {

}
