package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Medic_Information;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Medic_Information entity.
 */
public interface Medic_InformationSearchRepository extends ElasticsearchRepository<Medic_Information, Long> {
}
