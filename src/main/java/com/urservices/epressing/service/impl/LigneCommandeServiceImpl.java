package com.urservices.epressing.service.impl;

import com.urservices.epressing.service.LigneCommandeService;
import com.urservices.epressing.domain.LigneCommande;
import com.urservices.epressing.repository.LigneCommandeRepository;
import com.urservices.epressing.repository.search.LigneCommandeSearchRepository;
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
 * Service Implementation for managing LigneCommande.
 */
@Service
@Transactional
public class LigneCommandeServiceImpl implements LigneCommandeService{

    private final Logger log = LoggerFactory.getLogger(LigneCommandeServiceImpl.class);
    
    private final LigneCommandeRepository ligneCommandeRepository;

    private final LigneCommandeSearchRepository ligneCommandeSearchRepository;

    public LigneCommandeServiceImpl(LigneCommandeRepository ligneCommandeRepository, LigneCommandeSearchRepository ligneCommandeSearchRepository) {
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.ligneCommandeSearchRepository = ligneCommandeSearchRepository;
    }

    /**
     * Save a ligneCommande.
     *
     * @param ligneCommande the entity to save
     * @return the persisted entity
     */
    @Override
    public LigneCommande save(LigneCommande ligneCommande) {
        log.debug("Request to save LigneCommande : {}", ligneCommande);
        LigneCommande result = ligneCommandeRepository.save(ligneCommande);
        ligneCommandeSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the ligneCommandes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LigneCommande> findAll(Pageable pageable) {
        log.debug("Request to get all LigneCommandes");
        Page<LigneCommande> result = ligneCommandeRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one ligneCommande by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public LigneCommande findOne(Long id) {
        log.debug("Request to get LigneCommande : {}", id);
        LigneCommande ligneCommande = ligneCommandeRepository.findOne(id);
        return ligneCommande;
    }

    /**
     *  Delete the  ligneCommande by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete LigneCommande : {}", id);
        ligneCommandeRepository.delete(id);
        ligneCommandeSearchRepository.delete(id);
    }

    /**
     * Search for the ligneCommande corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<LigneCommande> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of LigneCommandes for query {}", query);
        Page<LigneCommande> result = ligneCommandeSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
