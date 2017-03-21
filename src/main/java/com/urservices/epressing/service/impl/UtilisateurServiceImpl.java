package com.urservices.epressing.service.impl;

import com.urservices.epressing.service.UtilisateurService;
import com.urservices.epressing.domain.Utilisateur;
import com.urservices.epressing.repository.UtilisateurRepository;
import com.urservices.epressing.repository.search.UtilisateurSearchRepository;
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
 * Service Implementation for managing Utilisateur.
 */
@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService{

    private final Logger log = LoggerFactory.getLogger(UtilisateurServiceImpl.class);
    
    private final UtilisateurRepository utilisateurRepository;

    private final UtilisateurSearchRepository utilisateurSearchRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository, UtilisateurSearchRepository utilisateurSearchRepository) {
        this.utilisateurRepository = utilisateurRepository;
        this.utilisateurSearchRepository = utilisateurSearchRepository;
    }

    /**
     * Save a utilisateur.
     *
     * @param utilisateur the entity to save
     * @return the persisted entity
     */
    @Override
    public Utilisateur save(Utilisateur utilisateur) {
        log.debug("Request to save Utilisateur : {}", utilisateur);
        Utilisateur result = utilisateurRepository.save(utilisateur);
        utilisateurSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the utilisateurs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Utilisateur> findAll(Pageable pageable) {
        log.debug("Request to get all Utilisateurs");
        Page<Utilisateur> result = utilisateurRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one utilisateur by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Utilisateur findOne(Long id) {
        log.debug("Request to get Utilisateur : {}", id);
        Utilisateur utilisateur = utilisateurRepository.findOne(id);
        return utilisateur;
    }

    /**
     *  Delete the  utilisateur by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Utilisateur : {}", id);
        utilisateurRepository.delete(id);
        utilisateurSearchRepository.delete(id);
    }

    /**
     * Search for the utilisateur corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Utilisateur> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Utilisateurs for query {}", query);
        Page<Utilisateur> result = utilisateurSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
