package com.urservices.epressing.repository.search;

import com.urservices.epressing.domain.LigneCommande;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the LigneCommande entity.
 */
public interface LigneCommandeSearchRepository extends ElasticsearchRepository<LigneCommande, Long> {
}
