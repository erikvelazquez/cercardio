package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Time;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Time entity.
 */
public interface TimeSearchRepository extends ElasticsearchRepository<Time, Long> {
}
