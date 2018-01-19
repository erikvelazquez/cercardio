package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.PacientGenerals;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PacientGenerals entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacientGeneralsRepository extends JpaRepository<PacientGenerals, Long> {

}
