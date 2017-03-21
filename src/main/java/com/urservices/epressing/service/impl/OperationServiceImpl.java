package com.urservices.epressing.service.impl;

import com.urservices.epressing.service.OperationService;
import com.urservices.epressing.domain.Operation;
import com.urservices.epressing.repository.OperationRepository;
import com.urservices.epressing.repository.search.OperationSearchRepository;
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
 * Service Implementation for managing Operation.
 */
@Service
@Transactional
public class OperationServiceImpl implements OperationService{

    private final Logger log = LoggerFactory.getLogger(OperationServiceImpl.class);
    
    private final OperationRepository operationRepository;

    private final OperationSearchRepository operationSearchRepository;

    public OperationServiceImpl(OperationRepository operationRepository, OperationSearchRepository operationSearchRepository) {
        this.operationRepository = operationRepository;
        this.operationSearchRepository = operationSearchRepository;
    }

    /**
     * Save a operation.
     *
     * @param operation the entity to save
     * @return the persisted entity
     */
    @Override
    public Operation save(Operation operation) {
        log.debug("Request to save Operation : {}", operation);
        Operation result = operationRepository.save(operation);
        operationSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the operations.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Operation> findAll(Pageable pageable) {
        log.debug("Request to get all Operations");
        Page<Operation> result = operationRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one operation by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Operation findOne(Long id) {
        log.debug("Request to get Operation : {}", id);
        Operation operation = operationRepository.findOne(id);
        return operation;
    }

    /**
     *  Delete the  operation by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Operation : {}", id);
        operationRepository.delete(id);
        operationSearchRepository.delete(id);
    }

    /**
     * Search for the operation corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Operation> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Operations for query {}", query);
        Page<Operation> result = operationSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
