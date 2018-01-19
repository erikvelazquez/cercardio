package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.AcademicDegree;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AcademicDegree entity.
 */
public interface AcademicDegreeSearchRepository extends ElasticsearchRepository<AcademicDegree, Long> {
}
