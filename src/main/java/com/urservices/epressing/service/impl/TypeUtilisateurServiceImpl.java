package com.urservices.epressing.service.impl;

import com.urservices.epressing.service.TypeUtilisateurService;
import com.urservices.epressing.domain.TypeUtilisateur;
import com.urservices.epressing.repository.TypeUtilisateurRepository;
import com.urservices.epressing.repository.search.TypeUtilisateurSearchRepository;
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
 * Service Implementation for managing TypeUtilisateur.
 */
@Service
@Transactional
public class TypeUtilisateurServiceImpl implements TypeUtilisateurService{

    private final Logger log = LoggerFactory.getLogger(TypeUtilisateurServiceImpl.class);
    
    private final TypeUtilisateurRepository typeUtilisateurRepository;

    private final TypeUtilisateurSearchRepository typeUtilisateurSearchRepository;

    public TypeUtilisateurServiceImpl(TypeUtilisateurRepository typeUtilisateurRepository, TypeUtilisateurSearchRepository typeUtilisateurSearchRepository) {
        this.typeUtilisateurRepository = typeUtilisateurRepository;
        this.typeUtilisateurSearchRepository = typeUtilisateurSearchRepository;
    }

    /**
     * Save a typeUtilisateur.
     *
     * @param typeUtilisateur the entity to save
     * @return the persisted entity
     */
    @Override
    public TypeUtilisateur save(TypeUtilisateur typeUtilisateur) {
        log.debug("Request to save TypeUtilisateur : {}", typeUtilisateur);
        TypeUtilisateur result = typeUtilisateurRepository.save(typeUtilisateur);
        typeUtilisateurSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the typeUtilisateurs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeUtilisateur> findAll(Pageable pageable) {
        log.debug("Request to get all TypeUtilisateurs");
        Page<TypeUtilisateur> result = typeUtilisateurRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one typeUtilisateur by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TypeUtilisateur findOne(Long id) {
        log.debug("Request to get TypeUtilisateur : {}", id);
        TypeUtilisateur typeUtilisateur = typeUtilisateurRepository.findOne(id);
        return typeUtilisateur;
    }

    /**
     *  Delete the  typeUtilisateur by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TypeUtilisateur : {}", id);
        typeUtilisateurRepository.delete(id);
        typeUtilisateurSearchRepository.delete(id);
    }

    /**
     * Search for the typeUtilisateur corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TypeUtilisateur> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TypeUtilisateurs for query {}", query);
        Page<TypeUtilisateur> result = typeUtilisateurSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
