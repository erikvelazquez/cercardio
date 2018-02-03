package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Way_of_Administration;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Way_of_Administration entity.
 */
public interface Way_of_AdministrationSearchRepository extends ElasticsearchRepository<Way_of_Administration, Long> {
}
