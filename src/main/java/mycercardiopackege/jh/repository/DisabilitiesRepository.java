package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Disabilities;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Disabilities entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DisabilitiesRepository extends JpaRepository<Disabilities, Long> {

}
