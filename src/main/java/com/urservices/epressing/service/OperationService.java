package com.urservices.epressing.service;

import com.urservices.epressing.domain.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * Service Interface for managing Operation.
 */
public interface OperationService {

    /**
     * Save a operation.
     *
     * @param operation the entity to save
     * @return the persisted entity
     */
    Operation save(Operation operation);

    /**
     *  Get all the operations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Operation> findAll(Pageable pageable);

    /**
     *  Get the "id" operation.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Operation findOne(Long id);

    /**
     *  Delete the "id" operation.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the operation corresponding to the query.
     *
     *  @param query the query of the search
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Operation> search(String query, Pageable pageable);
}
