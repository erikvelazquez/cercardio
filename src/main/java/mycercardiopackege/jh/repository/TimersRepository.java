package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Timers;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Timers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TimersRepository extends JpaRepository<Timers, Long> {

}
