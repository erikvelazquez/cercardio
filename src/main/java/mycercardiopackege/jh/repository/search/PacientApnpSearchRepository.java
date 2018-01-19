package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.PacientApnp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PacientApnp entity.
 */
public interface PacientApnpSearchRepository extends ElasticsearchRepository<PacientApnp, Long> {
}
