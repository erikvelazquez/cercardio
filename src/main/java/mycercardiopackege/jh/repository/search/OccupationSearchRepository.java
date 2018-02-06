package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Occupation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Occupation entity.
 */
public interface OccupationSearchRepository extends ElasticsearchRepository<Occupation, Long> {
}
