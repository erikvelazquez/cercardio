package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.AcademicDegree;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the AcademicDegree entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AcademicDegreeRepository extends JpaRepository<AcademicDegree, Long> {

}
