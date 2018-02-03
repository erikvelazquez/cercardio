package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.PrivateHealthInsurance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PrivateHealthInsurance entity.
 */
public interface PrivateHealthInsuranceSearchRepository extends ElasticsearchRepository<PrivateHealthInsurance, Long> {
}
