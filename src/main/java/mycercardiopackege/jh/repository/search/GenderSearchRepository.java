package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Gender;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Gender entity.
 */
public interface GenderSearchRepository extends ElasticsearchRepository<Gender, Long> {
}
