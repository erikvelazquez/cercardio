package mycercardiopackege.jh.repository.search;

import mycercardiopackege.jh.domain.Indigenous_Languages;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Indigenous_Languages entity.
 */
public interface Indigenous_LanguagesSearchRepository extends ElasticsearchRepository<Indigenous_Languages, Long> {
}
