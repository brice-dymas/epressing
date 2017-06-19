package com.urservices.epressing.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urservices.epressing.domain.Commande;
import com.urservices.epressing.service.CommandeService;
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
 * REST controller for managing Commande.
 */
@RestController
@RequestMapping("/api")
public class CommandeResource {

    private final Logger log = LoggerFactory.getLogger(CommandeResource.class);

    private static final String ENTITY_NAME = "commande";
        
    private final CommandeService commandeService;

    public CommandeResource(CommandeService commandeService) {
        this.commandeService = commandeService;
    }

    /**
     * POST  /commandes : Create a new commande.
     *
     * @param commande the commande to create
     * @return the ResponseEntity with status 201 (Created) and with body the new commande, or with status 400 (Bad Request) if the commande has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/commandes")
    @Timed
    public ResponseEntity<Commande> createCommande(@Valid @RequestBody Commande commande) throws URISyntaxException {
        log.debug("REST request to save Commande : {}", commande);
        if (commande.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new commande cannot already have an ID")).body(null);
        }
        Commande result = commandeService.save(commande);
        return ResponseEntity.created(new URI("/api/commandes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /commandes : Updates an existing commande.
     *
     * @param commande the commande to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated commande,
     * or with status 400 (Bad Request) if the commande is not valid,
     * or with status 500 (Internal Server Error) if the commande couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/commandes")
    @Timed
    public ResponseEntity<Commande> updateCommande(@Valid @RequestBody Commande commande) throws URISyntaxException {
        log.debug("REST request to update Commande : {}", commande);
        if (commande.getId() == null) {
            return createCommande(commande);
        }
        Commande result = commandeService.save(commande);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, commande.getId().toString()))
            .body(result);
    }

    /**
     * GET  /commandes : get all the commandes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commandes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/commandes")
    @Timed
    public ResponseEntity<List<Commande>> getAllCommandes(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Commandes");
        Page<Commande> page = commandeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commandes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /commandes : get all the commandes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of commandes in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/commandes/user/{id}")
    @Timed
    public ResponseEntity<List<Commande>> getAllCommandsOfCurrentUser(@PathVariable Long id, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Commandes of current user with id : {} ", id);
        Page<Commande> page = commandeService.findByUserIsCurrentUser(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/commandes/user/{id}");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /commandes/:id : get the "id" commande.
     *
     * @param id the id of the commande to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commande, or with status 404 (Not Found)
     */
    @GetMapping("/commandes/{id}")
    @Timed
    public ResponseEntity<Commande> getCommande(@PathVariable Long id) {
        log.debug("REST request to get Commande : {}", id);
        Commande commande = commandeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commande));
    }

    /**
     * DELETE  /commandes/:id : delete the "id" commande.
     *
     * @param id the id of the commande to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/commandes/{id}")
    @Timed
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        log.debug("REST request to delete Commande : {}", id);
        commandeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/commandes?query=:query : search for the commande corresponding
     * to the query.
     *
     * @param query the query of the commande search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/commandes")
    @Timed
    public ResponseEntity<List<Commande>> searchCommandes(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of Commandes for query {}", query);
        Page<Commande> page = commandeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/commandes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
