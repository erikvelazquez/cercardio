package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Medical_Procedures;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Medical_Procedures entity.
 */
public interface Medical_ProceduresSearchRepository extends ElasticsearchRepository<Medical_Procedures, Long> {
}
