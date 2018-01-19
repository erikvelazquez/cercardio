package mycercardiopackege.jh.repository;

import mycercardiopackege.jh.domain.Indigenous_Languages;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Indigenous_Languages entity.
 */
@SuppressWarnings("unused")
@Repository
public interface Indigenous_LanguagesRepository extends JpaRepository<Indigenous_Languages, Long> {

}
