package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.PacientContact;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PacientContact entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PacientContactRepository extends JpaRepository<PacientContact, Long> {

}
