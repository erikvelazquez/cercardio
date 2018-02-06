package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.PacientMedicalAnalysis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PacientMedicalAnalysis entity.
 */
public interface PacientMedicalAnalysisSearchRepository extends ElasticsearchRepository<PacientMedicalAnalysis, Long> {
}
