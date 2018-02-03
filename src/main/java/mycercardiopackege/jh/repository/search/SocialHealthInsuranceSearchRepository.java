package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.SocialHealthInsurance;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the SocialHealthInsurance entity.
 */
public interface SocialHealthInsuranceSearchRepository extends ElasticsearchRepository<SocialHealthInsurance, Long> {
}
