package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Nurse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Nurse entity.
 */
public interface NurseSearchRepository extends ElasticsearchRepository<Nurse, Long> {
}
