package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Domicile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Domicile entity.
 */
public interface DomicileSearchRepository extends ElasticsearchRepository<Domicile, Long> {
}
