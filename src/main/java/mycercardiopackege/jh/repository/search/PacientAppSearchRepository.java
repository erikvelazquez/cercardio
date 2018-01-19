package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.PacientApp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PacientApp entity.
 */
public interface PacientAppSearchRepository extends ElasticsearchRepository<PacientApp, Long> {
}
