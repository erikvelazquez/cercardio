package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.PacientGenerals;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PacientGenerals entity.
 */
public interface PacientGeneralsSearchRepository extends ElasticsearchRepository<PacientGenerals, Long> {
}
