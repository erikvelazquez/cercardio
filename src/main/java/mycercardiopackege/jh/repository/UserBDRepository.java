package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.UserBD;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserBD entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserBDRepository extends JpaRepository<UserBD, Long> {

}
