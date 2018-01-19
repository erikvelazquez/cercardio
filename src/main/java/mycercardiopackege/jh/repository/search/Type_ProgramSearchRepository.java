package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Type_Program;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Type_Program entity.
 */
public interface Type_ProgramSearchRepository extends ElasticsearchRepository<Type_Program, Long> {
}
