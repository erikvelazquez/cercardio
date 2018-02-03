package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Programs;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Programs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProgramsRepository extends JpaRepository<Programs, Long> {

}
