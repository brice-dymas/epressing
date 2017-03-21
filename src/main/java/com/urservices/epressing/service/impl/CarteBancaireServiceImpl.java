package com.urservices.epressing.service.impl;

import com.urservices.epressing.service.CarteBancaireService;
import com.urservices.epressing.domain.CarteBancaire;
import com.urservices.epressing.repository.CarteBancaireRepository;
import com.urservices.epressing.repository.search.CarteBancaireSearchRepository;
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
 * Service Implementation for managing CarteBancaire.
 */
@Service
@Transactional
public class CarteBancaireServiceImpl implements CarteBancaireService{

    private final Logger log = LoggerFactory.getLogger(CarteBancaireServiceImpl.class);
    
    private final CarteBancaireRepository carteBancaireRepository;

    private final CarteBancaireSearchRepository carteBancaireSearchRepository;

    public CarteBancaireServiceImpl(CarteBancaireRepository carteBancaireRepository, CarteBancaireSearchRepository carteBancaireSearchRepository) {
        this.carteBancaireRepository = carteBancaireRepository;
        this.carteBancaireSearchRepository = carteBancaireSearchRepository;
    }

    /**
     * Save a carteBancaire.
     *
     * @param carteBancaire the entity to save
     * @return the persisted entity
     */
    @Override
    public CarteBancaire save(CarteBancaire carteBancaire) {
        log.debug("Request to save CarteBancaire : {}", carteBancaire);
        CarteBancaire result = carteBancaireRepository.save(carteBancaire);
        carteBancaireSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the carteBancaires.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CarteBancaire> findAll(Pageable pageable) {
        log.debug("Request to get all CarteBancaires");
        Page<CarteBancaire> result = carteBancaireRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one carteBancaire by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public CarteBancaire findOne(Long id) {
        log.debug("Request to get CarteBancaire : {}", id);
        CarteBancaire carteBancaire = carteBancaireRepository.findOne(id);
        return carteBancaire;
    }

    /**
     *  Delete the  carteBancaire by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CarteBancaire : {}", id);
        carteBancaireRepository.delete(id);
        carteBancaireSearchRepository.delete(id);
    }

    /**
     * Search for the carteBancaire corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CarteBancaire> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CarteBancaires for query {}", query);
        Page<CarteBancaire> result = carteBancaireSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
