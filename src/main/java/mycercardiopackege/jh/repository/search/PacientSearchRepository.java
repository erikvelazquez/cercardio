package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Pacient;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Pacient entity.
 */
public interface PacientSearchRepository extends ElasticsearchRepository<Pacient, Long> {
}
