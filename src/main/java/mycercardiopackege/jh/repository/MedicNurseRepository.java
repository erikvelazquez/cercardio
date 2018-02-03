package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.MedicNurse;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the MedicNurse entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicNurseRepository extends JpaRepository<MedicNurse, Long> {
    @Query("select distinct medic_nurse from MedicNurse medic_nurse left join fetch medic_nurse.nurses")
    List<MedicNurse> findAllWithEagerRelationships();

    @Query("select medic_nurse from MedicNurse medic_nurse left join fetch medic_nurse.nurses where medic_nurse.id =:id")
    MedicNurse findOneWithEagerRelationships(@Param("id") Long id);

}
