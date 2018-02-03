package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.MedicNurse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MedicNurse entity.
 */
public interface MedicNurseSearchRepository extends ElasticsearchRepository<MedicNurse, Long> {
}
