package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.PacientContact;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PacientContact entity.
 */
public interface PacientContactSearchRepository extends ElasticsearchRepository<PacientContact, Long> {
}
