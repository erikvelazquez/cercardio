package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Nurse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Nurse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {

}
