package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.MedicalAnalysis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MedicalAnalysis entity.
 */
public interface MedicalAnalysisSearchRepository extends ElasticsearchRepository<MedicalAnalysis, Long> {
}
