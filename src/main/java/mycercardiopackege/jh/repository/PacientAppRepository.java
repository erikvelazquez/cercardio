package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.PacientApp;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PacientApp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacientAppRepository extends JpaRepository<PacientApp, Long> {

}
