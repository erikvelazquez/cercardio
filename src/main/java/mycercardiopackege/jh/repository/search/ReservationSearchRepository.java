package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Reservation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Reservation entity.
 */
public interface ReservationSearchRepository extends ElasticsearchRepository<Reservation, Long> {
}
