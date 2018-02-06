package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Status;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Status entity.
 */
public interface StatusSearchRepository extends ElasticsearchRepository<Status, Long> {
}
