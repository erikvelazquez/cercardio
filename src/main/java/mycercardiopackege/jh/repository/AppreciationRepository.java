package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Appreciation;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Appreciation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AppreciationRepository extends JpaRepository<Appreciation, Long> {

}
