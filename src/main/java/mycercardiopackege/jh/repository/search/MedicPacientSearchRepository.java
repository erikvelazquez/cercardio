package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.MedicPacient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the MedicPacient entity.
 */
public interface MedicPacientSearchRepository extends ElasticsearchRepository<MedicPacient, Long> {
}
