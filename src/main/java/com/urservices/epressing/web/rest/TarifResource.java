package com.urservices.epressing.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urservices.epressing.domain.Tarif;
import com.urservices.epressing.service.TarifService;
import com.urservices.epressing.web.rest.util.HeaderUtil;
import com.urservices.epressing.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Tarif.
 */
@RestController
@RequestMapping("/api")
public class TarifResource {

    private final Logger log = LoggerFactory.getLogger(TarifResource.class);

    private static final String ENTITY_NAME = "tarif";
        
    private final TarifService tarifService;

    public TarifResource(TarifService tarifService) {
        this.tarifService = tarifService;
    }

    /**
     * POST  /tarifs : Create a new tarif.
     *
     * @param tarif the tarif to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tarif, or with status 400 (Bad Request) if the tarif has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tarifs")
    @Timed
    public ResponseEntity<Tarif> createTarif(@Valid @RequestBody Tarif tarif) throws URISyntaxException {
        log.debug("REST request to save Tarif : {}", tarif);
        if (tarif.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new tarif cannot already have an ID")).body(null);
        }
        Tarif result = tarifService.save(tarif);
        return ResponseEntity.created(new URI("/api/tarifs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tarifs : Updates an existing tarif.
     *
     * @param tarif the tarif to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tarif,
     * or with status 400 (Bad Request) if the tarif is not valid,
     * or with status 500 (Internal Server Error) if the tarif couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tarifs")
    @Timed
    public ResponseEntity<Tarif> updateTarif(@Valid @RequestBody Tarif tarif) throws URISyntaxException {
        log.debug("REST request to update Tarif : {}", tarif);
        if (tarif.getId() == null) {
            return createTarif(tarif);
        }
        Tarif result = tarifService.save(tarif);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tarif.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tarifs : get all the tarifs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of tarifs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/tarifs")
    @Timed
    public ResponseEntity<List<Tarif>> getAllTarifs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Tarifs");
        Page<Tarif> page = tarifService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tarifs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tarifs/:id : get the "id" tarif.
     *
     * @param id the id of the tarif to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tarif, or with status 404 (Not Found)
     */
    @GetMapping("/tarifs/{id}")
    @Timed
    public ResponseEntity<Tarif> getTarif(@PathVariable Long id) {
        log.debug("REST request to get Tarif : {}", id);
        Tarif tarif = tarifService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tarif));
    }
    /**
     * GET /tarifs/operation/:idOperation/produit/:idProduit
     */
    @GetMapping("/tarifs/operation/{idOperation}/produit/{idProduit]")
    @Timed
    public ResponseEntity<Tarif> getTarifByOperationAndProduct(@PathVariable Long idOperation, @PathVariable Long idProduit) {
        log.debug("REST request to get Tarif  of Operation : {} and Product : {}", idOperation, idProduit);
        Tarif tarif = tarifService.findByOperationAndProduct(idOperation, idProduit);
        System.out.println("le Tarif trouvé a pour ID " + tarif.getId() );
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tarif));
    }
    
    /**
     * DELETE  /tarifs/:id : delete the "id" tarif.
     *
     * @param id the id of the tarif to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tarifs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTarif(@PathVariable Long id) {
        log.debug("REST request to delete Tarif : {}", id);
        tarifService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/tarifs?query=:query : search for the tarif corresponding
     * to the query.
     *
     * @param query the query of the tarif search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/tarifs")
    @Timed
    public ResponseEntity<List<Tarif>> searchTarifs(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Tarifs for query {}", query);
        Page<Tarif> page = tarifService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/tarifs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
