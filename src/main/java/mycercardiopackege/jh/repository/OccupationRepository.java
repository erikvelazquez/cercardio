package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Occupation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Occupation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OccupationRepository extends JpaRepository<Occupation, Long> {

}
