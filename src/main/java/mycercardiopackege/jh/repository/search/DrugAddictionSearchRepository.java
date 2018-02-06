package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.DrugAddiction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DrugAddiction entity.
 */
public interface DrugAddictionSearchRepository extends ElasticsearchRepository<DrugAddiction, Long> {
}
