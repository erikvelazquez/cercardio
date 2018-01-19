package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Medic;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Medic entity.
 */
public interface MedicSearchRepository extends ElasticsearchRepository<Medic, Long> {
}
