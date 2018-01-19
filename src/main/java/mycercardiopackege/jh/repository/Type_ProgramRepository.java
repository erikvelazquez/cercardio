package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Type_Program;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Type_Program entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Type_ProgramRepository extends JpaRepository<Type_Program, Long> {

}
