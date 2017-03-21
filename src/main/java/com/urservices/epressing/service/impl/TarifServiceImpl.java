package com.urservices.epressing.service.impl;

import com.urservices.epressing.service.TarifService;
import com.urservices.epressing.domain.Tarif;
import com.urservices.epressing.repository.TarifRepository;
import com.urservices.epressing.repository.search.TarifSearchRepository;
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
 * Service Implementation for managing Tarif.
 */
@Service
@Transactional
public class TarifServiceImpl implements TarifService{

    private final Logger log = LoggerFactory.getLogger(TarifServiceImpl.class);
    
    private final TarifRepository tarifRepository;

    private final TarifSearchRepository tarifSearchRepository;

    public TarifServiceImpl(TarifRepository tarifRepository, TarifSearchRepository tarifSearchRepository) {
        this.tarifRepository = tarifRepository;
        this.tarifSearchRepository = tarifSearchRepository;
    }

    /**
     * Save a tarif.
     *
     * @param tarif the entity to save
     * @return the persisted entity
     */
    @Override
    public Tarif save(Tarif tarif) {
        log.debug("Request to save Tarif : {}", tarif);
        Tarif result = tarifRepository.save(tarif);
        tarifSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the tarifs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Tarif> findAll(Pageable pageable) {
        log.debug("Request to get all Tarifs");
        Page<Tarif> result = tarifRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one tarif by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Tarif findOne(Long id) {
        log.debug("Request to get Tarif : {}", id);
        Tarif tarif = tarifRepository.findOne(id);
        return tarif;
    }

    /**
     *  Delete the  tarif by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Tarif : {}", id);
        tarifRepository.delete(id);
        tarifSearchRepository.delete(id);
    }

    /**
     * Search for the tarif corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Tarif> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Tarifs for query {}", query);
        Page<Tarif> result = tarifSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
