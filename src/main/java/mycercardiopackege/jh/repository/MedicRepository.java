package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Medic;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Medic entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicRepository extends JpaRepository<Medic, Long> {

}
