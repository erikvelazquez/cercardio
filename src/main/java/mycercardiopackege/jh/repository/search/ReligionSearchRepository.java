package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Religion;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Religion entity.
 */
public interface ReligionSearchRepository extends ElasticsearchRepository<Religion, Long> {
}
