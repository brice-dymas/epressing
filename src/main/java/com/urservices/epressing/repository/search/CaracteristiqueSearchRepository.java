package com.urservices.epressing.repository.search;

import com.urservices.epressing.domain.Caracteristique;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Caracteristique entity.
 */
public interface CaracteristiqueSearchRepository extends ElasticsearchRepository<Caracteristique, Long> {
}
