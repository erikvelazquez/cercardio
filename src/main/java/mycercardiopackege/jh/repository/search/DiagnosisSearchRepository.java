package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Diagnosis;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Diagnosis entity.
 */
public interface DiagnosisSearchRepository extends ElasticsearchRepository<Diagnosis, Long> {
}
