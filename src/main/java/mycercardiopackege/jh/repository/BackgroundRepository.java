package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Background;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Background entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BackgroundRepository extends JpaRepository<Background, Long> {

}
