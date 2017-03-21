package com.urservices.epressing.service;

import com.urservices.epressing.domain.Utilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Utilisateur.
 */
public interface UtilisateurService {

    /**
     * Save a utilisateur.
     *
     * @param utilisateur the entity to save
     * @return the persisted entity
     */
    Utilisateur save(Utilisateur utilisateur);

    /**
     *  Get all the utilisateurs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Utilisateur> findAll(Pageable pageable);

    /**
     *  Get the "id" utilisateur.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Utilisateur findOne(Long id);

    /**
     *  Delete the "id" utilisateur.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the utilisateur corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Utilisateur> search(String query, Pageable pageable);
}
