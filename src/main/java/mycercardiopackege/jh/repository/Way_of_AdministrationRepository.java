package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Way_of_Administration;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Way_of_Administration entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Way_of_AdministrationRepository extends JpaRepository<Way_of_Administration, Long> {

}
