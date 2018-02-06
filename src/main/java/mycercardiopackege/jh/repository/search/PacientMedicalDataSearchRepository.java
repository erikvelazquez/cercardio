package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.PacientMedicalData;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PacientMedicalData entity.
 */
public interface PacientMedicalDataSearchRepository extends ElasticsearchRepository<PacientMedicalData, Long> {
}
