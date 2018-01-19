package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.SocioeconomicLevel;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SocioeconomicLevel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SocioeconomicLevelRepository extends JpaRepository<SocioeconomicLevel, Long> {

}
