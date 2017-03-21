package com.urservices.epressing.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urservices.epressing.domain.CarteBancaire;
import com.urservices.epressing.service.CarteBancaireService;
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
 * REST controller for managing CarteBancaire.
 */
@RestController
@RequestMapping("/api")
public class CarteBancaireResource {

    private final Logger log = LoggerFactory.getLogger(CarteBancaireResource.class);

    private static final String ENTITY_NAME = "carteBancaire";
        
    private final CarteBancaireService carteBancaireService;

    public CarteBancaireResource(CarteBancaireService carteBancaireService) {
        this.carteBancaireService = carteBancaireService;
    }

    /**
     * POST  /carte-bancaires : Create a new carteBancaire.
     *
     * @param carteBancaire the carteBancaire to create
     * @return the ResponseEntity with status 201 (Created) and with body the new carteBancaire, or with status 400 (Bad Request) if the carteBancaire has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/carte-bancaires")
    @Timed
    public ResponseEntity<CarteBancaire> createCarteBancaire(@Valid @RequestBody CarteBancaire carteBancaire) throws URISyntaxException {
        log.debug("REST request to save CarteBancaire : {}", carteBancaire);
        if (carteBancaire.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new carteBancaire cannot already have an ID")).body(null);
        }
        CarteBancaire result = carteBancaireService.save(carteBancaire);
        return ResponseEntity.created(new URI("/api/carte-bancaires/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /carte-bancaires : Updates an existing carteBancaire.
     *
     * @param carteBancaire the carteBancaire to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated carteBancaire,
     * or with status 400 (Bad Request) if the carteBancaire is not valid,
     * or with status 500 (Internal Server Error) if the carteBancaire couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/carte-bancaires")
    @Timed
    public ResponseEntity<CarteBancaire> updateCarteBancaire(@Valid @RequestBody CarteBancaire carteBancaire) throws URISyntaxException {
        log.debug("REST request to update CarteBancaire : {}", carteBancaire);
        if (carteBancaire.getId() == null) {
            return createCarteBancaire(carteBancaire);
        }
        CarteBancaire result = carteBancaireService.save(carteBancaire);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, carteBancaire.getId().toString()))
            .body(result);
    }

    /**
     * GET  /carte-bancaires : get all the carteBancaires.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of carteBancaires in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/carte-bancaires")
    @Timed
    public ResponseEntity<List<CarteBancaire>> getAllCarteBancaires(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of CarteBancaires");
        Page<CarteBancaire> page = carteBancaireService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/carte-bancaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /carte-bancaires/:id : get the "id" carteBancaire.
     *
     * @param id the id of the carteBancaire to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the carteBancaire, or with status 404 (Not Found)
     */
    @GetMapping("/carte-bancaires/{id}")
    @Timed
    public ResponseEntity<CarteBancaire> getCarteBancaire(@PathVariable Long id) {
        log.debug("REST request to get CarteBancaire : {}", id);
        CarteBancaire carteBancaire = carteBancaireService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(carteBancaire));
    }

    /**
     * DELETE  /carte-bancaires/:id : delete the "id" carteBancaire.
     *
     * @param id the id of the carteBancaire to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/carte-bancaires/{id}")
    @Timed
    public ResponseEntity<Void> deleteCarteBancaire(@PathVariable Long id) {
        log.debug("REST request to delete CarteBancaire : {}", id);
        carteBancaireService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/carte-bancaires?query=:query : search for the carteBancaire corresponding
     * to the query.
     *
     * @param query the query of the carteBancaire search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/carte-bancaires")
    @Timed
    public ResponseEntity<List<CarteBancaire>> searchCarteBancaires(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of CarteBancaires for query {}", query);
        Page<CarteBancaire> page = carteBancaireService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/carte-bancaires");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
