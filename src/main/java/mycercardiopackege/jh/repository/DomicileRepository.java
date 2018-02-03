package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Domicile;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Domicile entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DomicileRepository extends JpaRepository<Domicile, Long> {

}
