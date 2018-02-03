package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Appreciation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Appreciation entity.
 */
public interface AppreciationSearchRepository extends ElasticsearchRepository<Appreciation, Long> {
}
