package com.urservices.epressing.repository.search;

import com.urservices.epressing.domain.CarteBancaire;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CarteBancaire entity.
 */
public interface CarteBancaireSearchRepository extends ElasticsearchRepository<CarteBancaire, Long> {
}
