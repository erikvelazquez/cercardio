package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.EthnicGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the EthnicGroup entity.
 */
public interface EthnicGroupSearchRepository extends ElasticsearchRepository<EthnicGroup, Long> {
}
