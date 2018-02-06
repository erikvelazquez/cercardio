package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Pacient;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Pacient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacientRepository extends JpaRepository<Pacient, Long> {

}
