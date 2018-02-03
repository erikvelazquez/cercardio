package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.PacientApnp;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PacientApnp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacientApnpRepository extends JpaRepository<PacientApnp, Long> {

}
