package com.urservices.epressing.repository.search;

import com.urservices.epressing.domain.Tarif;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Tarif entity.
 */
public interface TarifSearchRepository extends ElasticsearchRepository<Tarif, Long> {
}
