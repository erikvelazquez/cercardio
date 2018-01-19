package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.PacientLaboratoy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PacientLaboratoy entity.
 */
public interface PacientLaboratoySearchRepository extends ElasticsearchRepository<PacientLaboratoy, Long> {
}
