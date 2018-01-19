package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.BloodGroup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BloodGroup entity.
 */
public interface BloodGroupSearchRepository extends ElasticsearchRepository<BloodGroup, Long> {
}
