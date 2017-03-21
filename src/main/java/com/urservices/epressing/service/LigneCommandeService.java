package com.urservices.epressing.service;

import com.urservices.epressing.domain.LigneCommande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing LigneCommande.
 */
public interface LigneCommandeService {

    /**
     * Save a ligneCommande.
     *
     * @param ligneCommande the entity to save
     * @return the persisted entity
     */
    LigneCommande save(LigneCommande ligneCommande);

    /**
     *  Get all the ligneCommandes.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LigneCommande> findAll(Pageable pageable);

    /**
     *  Get the "id" ligneCommande.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    LigneCommande findOne(Long id);

    /**
     *  Delete the "id" ligneCommande.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the ligneCommande corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<LigneCommande> search(String query, Pageable pageable);
}
