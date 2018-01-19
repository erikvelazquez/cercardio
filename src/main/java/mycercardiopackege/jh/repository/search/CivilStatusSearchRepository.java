package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.CivilStatus;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CivilStatus entity.
 */
public interface CivilStatusSearchRepository extends ElasticsearchRepository<CivilStatus, Long> {
}
