package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.BloodGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the BloodGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BloodGroupRepository extends JpaRepository<BloodGroup, Long> {

}
