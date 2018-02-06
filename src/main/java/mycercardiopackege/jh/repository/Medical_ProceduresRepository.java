package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Medical_Procedures;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Medical_Procedures entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Medical_ProceduresRepository extends JpaRepository<Medical_Procedures, Long> {

}
