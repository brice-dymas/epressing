package com.urservices.epressing.service;

import com.urservices.epressing.domain.TypeUtilisateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing TypeUtilisateur.
 */
public interface TypeUtilisateurService {

    /**
     * Save a typeUtilisateur.
     *
     * @param typeUtilisateur the entity to save
     * @return the persisted entity
     */
    TypeUtilisateur save(TypeUtilisateur typeUtilisateur);

    /**
     *  Get all the typeUtilisateurs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TypeUtilisateur> findAll(Pageable pageable);

    /**
     *  Get the "id" typeUtilisateur.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    TypeUtilisateur findOne(Long id);

    /**
     *  Delete the "id" typeUtilisateur.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the typeUtilisateur corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<TypeUtilisateur> search(String query, Pageable pageable);
}
