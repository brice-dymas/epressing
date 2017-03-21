package com.urservices.epressing.service;

import com.urservices.epressing.domain.CarteBancaire;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing CarteBancaire.
 */
public interface CarteBancaireService {

    /**
     * Save a carteBancaire.
     *
     * @param carteBancaire the entity to save
     * @return the persisted entity
     */
    CarteBancaire save(CarteBancaire carteBancaire);

    /**
     *  Get all the carteBancaires.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CarteBancaire> findAll(Pageable pageable);

    /**
     *  Get the "id" carteBancaire.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    CarteBancaire findOne(Long id);

    /**
     *  Delete the "id" carteBancaire.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the carteBancaire corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<CarteBancaire> search(String query, Pageable pageable);
}
