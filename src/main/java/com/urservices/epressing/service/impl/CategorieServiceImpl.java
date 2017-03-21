package com.urservices.epressing.service.impl;

import com.urservices.epressing.service.CategorieService;
import com.urservices.epressing.domain.Categorie;
import com.urservices.epressing.repository.CategorieRepository;
import com.urservices.epressing.repository.search.CategorieSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Categorie.
 */
@Service
@Transactional
public class CategorieServiceImpl implements CategorieService{

    private final Logger log = LoggerFactory.getLogger(CategorieServiceImpl.class);
    
    private final CategorieRepository categorieRepository;

    private final CategorieSearchRepository categorieSearchRepository;

    public CategorieServiceImpl(CategorieRepository categorieRepository, CategorieSearchRepository categorieSearchRepository) {
        this.categorieRepository = categorieRepository;
        this.categorieSearchRepository = categorieSearchRepository;
    }

    /**
     * Save a categorie.
     *
     * @param categorie the entity to save
     * @return the persisted entity
     */
    @Override
    public Categorie save(Categorie categorie) {
        log.debug("Request to save Categorie : {}", categorie);
        Categorie result = categorieRepository.save(categorie);
        categorieSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the categories.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Categorie> findAll(Pageable pageable) {
        log.debug("Request to get all Categories");
        Page<Categorie> result = categorieRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one categorie by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Categorie findOne(Long id) {
        log.debug("Request to get Categorie : {}", id);
        Categorie categorie = categorieRepository.findOne(id);
        return categorie;
    }

    /**
     *  Delete the  categorie by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Categorie : {}", id);
        categorieRepository.delete(id);
        categorieSearchRepository.delete(id);
    }

    /**
     * Search for the categorie corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Categorie> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Categories for query {}", query);
        Page<Categorie> result = categorieSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
