package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.LivingPlace;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the LivingPlace entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LivingPlaceRepository extends JpaRepository<LivingPlace, Long> {

}
