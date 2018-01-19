package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Medicine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Medicine entity.
 */
public interface MedicineSearchRepository extends ElasticsearchRepository<Medicine, Long> {
}
