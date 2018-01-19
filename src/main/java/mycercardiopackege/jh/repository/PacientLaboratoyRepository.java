package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.PacientLaboratoy;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PacientLaboratoy entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacientLaboratoyRepository extends JpaRepository<PacientLaboratoy, Long> {

}
