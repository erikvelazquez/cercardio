package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.DrugAddiction;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the DrugAddiction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DrugAddictionRepository extends JpaRepository<DrugAddiction, Long> {

}
