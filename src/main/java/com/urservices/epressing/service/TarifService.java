package com.urservices.epressing.service;

import com.urservices.epressing.domain.Tarif;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Tarif.
 */
public interface TarifService {

    /**
     * Save a tarif.
     *
     * @param tarif the entity to save
     * @return the persisted entity
     */
    Tarif save(Tarif tarif);

    /**
     *  Get all the tarifs.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tarif> findAll(Pageable pageable);

    /**
     *  Get the "id" tarif.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Tarif findOne(Long id);

    Tarif findByOperationAndProduct(Long idOperation, Long idProduit);

    public List<Tarif> findByProductID(Long  idProduit);

    /**
     *  Delete the "id" tarif.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the tarif corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Tarif> search(String query, Pageable pageable);
}
