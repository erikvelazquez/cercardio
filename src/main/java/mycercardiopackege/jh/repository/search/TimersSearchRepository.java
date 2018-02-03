package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Timers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Timers entity.
 */
public interface TimersSearchRepository extends ElasticsearchRepository<Timers, Long> {
}
