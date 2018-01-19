package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Medic_Information;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Medic_Information entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Medic_InformationRepository extends JpaRepository<Medic_Information, Long> {

}
