package com.urservices.epressing.repository.search;

import com.urservices.epressing.domain.TypeUtilisateur;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the TypeUtilisateur entity.
 */
public interface TypeUtilisateurSearchRepository extends ElasticsearchRepository<TypeUtilisateur, Long> {
}
