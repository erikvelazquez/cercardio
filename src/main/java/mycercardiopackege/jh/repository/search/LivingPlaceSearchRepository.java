package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.LivingPlace;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LivingPlace entity.
 */
public interface LivingPlaceSearchRepository extends ElasticsearchRepository<LivingPlace, Long> {
}
