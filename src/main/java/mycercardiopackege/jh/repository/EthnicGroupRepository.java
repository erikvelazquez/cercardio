package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.EthnicGroup;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the EthnicGroup entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EthnicGroupRepository extends JpaRepository<EthnicGroup, Long> {

}
