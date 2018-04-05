package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.SocioEconomicLevel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SocioeconomicLevel entity.
 */
public interface SocioEconomicLevelSearchRepository extends ElasticsearchRepository<SocioEconomicLevel, Long> {
}
