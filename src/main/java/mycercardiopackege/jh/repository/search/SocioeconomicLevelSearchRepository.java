package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.SocioeconomicLevel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SocioeconomicLevel entity.
 */
public interface SocioeconomicLevelSearchRepository extends ElasticsearchRepository<SocioeconomicLevel, Long> {
}
