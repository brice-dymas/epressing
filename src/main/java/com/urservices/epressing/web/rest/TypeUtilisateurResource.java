package com.urservices.epressing.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urservices.epressing.domain.TypeUtilisateur;
import com.urservices.epressing.service.TypeUtilisateurService;
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
 * REST controller for managing TypeUtilisateur.
 */
@RestController
@RequestMapping("/api")
public class TypeUtilisateurResource {

    private final Logger log = LoggerFactory.getLogger(TypeUtilisateurResource.class);

    private static final String ENTITY_NAME = "typeUtilisateur";
        
    private final TypeUtilisateurService typeUtilisateurService;

    public TypeUtilisateurResource(TypeUtilisateurService typeUtilisateurService) {
        this.typeUtilisateurService = typeUtilisateurService;
    }

    /**
     * POST  /type-utilisateurs : Create a new typeUtilisateur.
     *
     * @param typeUtilisateur the typeUtilisateur to create
     * @return the ResponseEntity with status 201 (Created) and with body the new typeUtilisateur, or with status 400 (Bad Request) if the typeUtilisateur has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/type-utilisateurs")
    @Timed
    public ResponseEntity<TypeUtilisateur> createTypeUtilisateur(@Valid @RequestBody TypeUtilisateur typeUtilisateur) throws URISyntaxException {
        log.debug("REST request to save TypeUtilisateur : {}", typeUtilisateur);
        if (typeUtilisateur.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new typeUtilisateur cannot already have an ID")).body(null);
        }
        TypeUtilisateur result = typeUtilisateurService.save(typeUtilisateur);
        return ResponseEntity.created(new URI("/api/type-utilisateurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /type-utilisateurs : Updates an existing typeUtilisateur.
     *
     * @param typeUtilisateur the typeUtilisateur to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated typeUtilisateur,
     * or with status 400 (Bad Request) if the typeUtilisateur is not valid,
     * or with status 500 (Internal Server Error) if the typeUtilisateur couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/type-utilisateurs")
    @Timed
    public ResponseEntity<TypeUtilisateur> updateTypeUtilisateur(@Valid @RequestBody TypeUtilisateur typeUtilisateur) throws URISyntaxException {
        log.debug("REST request to update TypeUtilisateur : {}", typeUtilisateur);
        if (typeUtilisateur.getId() == null) {
            return createTypeUtilisateur(typeUtilisateur);
        }
        TypeUtilisateur result = typeUtilisateurService.save(typeUtilisateur);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, typeUtilisateur.getId().toString()))
            .body(result);
    }

    /**
     * GET  /type-utilisateurs : get all the typeUtilisateurs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of typeUtilisateurs in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/type-utilisateurs")
    @Timed
    public ResponseEntity<List<TypeUtilisateur>> getAllTypeUtilisateurs(@ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of TypeUtilisateurs");
        Page<TypeUtilisateur> page = typeUtilisateurService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/type-utilisateurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /type-utilisateurs/:id : get the "id" typeUtilisateur.
     *
     * @param id the id of the typeUtilisateur to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the typeUtilisateur, or with status 404 (Not Found)
     */
    @GetMapping("/type-utilisateurs/{id}")
    @Timed
    public ResponseEntity<TypeUtilisateur> getTypeUtilisateur(@PathVariable Long id) {
        log.debug("REST request to get TypeUtilisateur : {}", id);
        TypeUtilisateur typeUtilisateur = typeUtilisateurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(typeUtilisateur));
    }

    /**
     * DELETE  /type-utilisateurs/:id : delete the "id" typeUtilisateur.
     *
     * @param id the id of the typeUtilisateur to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/type-utilisateurs/{id}")
    @Timed
    public ResponseEntity<Void> deleteTypeUtilisateur(@PathVariable Long id) {
        log.debug("REST request to delete TypeUtilisateur : {}", id);
        typeUtilisateurService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/type-utilisateurs?query=:query : search for the typeUtilisateur corresponding
     * to the query.
     *
     * @param query the query of the typeUtilisateur search 
     * @param pageable the pagination information
     * @return the result of the search
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/_search/type-utilisateurs")
    @Timed
    public ResponseEntity<List<TypeUtilisateur>> searchTypeUtilisateurs(@RequestParam String query, @ApiParam Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to search for a page of TypeUtilisateurs for query {}", query);
        Page<TypeUtilisateur> page = typeUtilisateurService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/type-utilisateurs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


}
