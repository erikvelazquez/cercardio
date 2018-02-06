package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.CivilStatus;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the CivilStatus entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CivilStatusRepository extends JpaRepository<CivilStatus, Long> {

}
