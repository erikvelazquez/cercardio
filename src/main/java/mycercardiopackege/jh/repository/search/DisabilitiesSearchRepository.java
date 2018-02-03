package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Disabilities;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Disabilities entity.
 */
public interface DisabilitiesSearchRepository extends ElasticsearchRepository<Disabilities, Long> {
}
