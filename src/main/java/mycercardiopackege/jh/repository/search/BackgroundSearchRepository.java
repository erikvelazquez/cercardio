package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Background;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Background entity.
 */
public interface BackgroundSearchRepository extends ElasticsearchRepository<Background, Long> {
}
