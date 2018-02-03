package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.UserBD;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserBD entity.
 */
public interface UserBDSearchRepository extends ElasticsearchRepository<UserBD, Long> {
}
