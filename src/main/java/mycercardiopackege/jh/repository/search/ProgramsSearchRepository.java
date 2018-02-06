package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Programs;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Programs entity.
 */
public interface ProgramsSearchRepository extends ElasticsearchRepository<Programs, Long> {
}
