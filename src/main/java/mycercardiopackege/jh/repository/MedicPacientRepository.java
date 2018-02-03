package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.MedicPacient;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the MedicPacient entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MedicPacientRepository extends JpaRepository<MedicPacient, Long> {
    @Query("select distinct medic_pacient from MedicPacient medic_pacient left join fetch medic_pacient.pacients")
    List<MedicPacient> findAllWithEagerRelationships();

    @Query("select medic_pacient from MedicPacient medic_pacient left join fetch medic_pacient.pacients where medic_pacient.id =:id")
    MedicPacient findOneWithEagerRelationships(@Param("id") Long id);

}
