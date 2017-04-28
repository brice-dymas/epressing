/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.urservices.epressing.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.urservices.epressing.domain.CommandeForm;
import com.urservices.epressing.service.commandeFormService;
import com.urservices.epressing.web.rest.util.HeaderUtil;
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

/**
 *
 * @author sando
 */
@RestController
@RequestMapping("/api")
public class CommandeFormResource
{

    private final Logger log = LoggerFactory.getLogger(LigneCommandeResource.class);

    private static final String ENTITY_NAME = "commandeForm";

    private final commandeFormService commandeFormService;

    public CommandeFormResource(commandeFormService commandeFormService)
    {
        this.commandeFormService = commandeFormService;
    }

    @PostMapping("/commandes/commandeForms")
    @Timed
    public ResponseEntity<CommandeForm> saveCommand(@Valid @RequestBody CommandeForm commandeForm) throws URISyntaxException
    {
        log.debug("REST request to save CommandeForm : {}", commandeForm);
        if (commandeForm.getCommande().getId() != null && commandeForm.getCommande().getId() != 0)
        {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new CommandeForm cannot be null")).body(null);
        }

        System.out.print("Dans CommandeFormResource le nombre d'élément a save est de ");
        System.out.println(commandeForm.getLigneCommandes().size());
        System.out.print(commandeForm);
        CommandeForm result = commandeFormService.save(commandeForm);
        return ResponseEntity.created(new URI("/api/commandeForm/" + result.getCommande().getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getCommande().getId().toString()))
                .body(result); 
    }

      /**
     * GET  /commandeComplete/:id : get the "id" commande.
     *
     * @param id the id of the commande to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the commande, or with status 404 (Not Found)
     */
    @GetMapping("/commandes/commandeComplete/{id}")
    @Timed
    public ResponseEntity<CommandeForm> getCommandeComplete(@PathVariable Long id) {
        log.debug("REST request to get Commande : {}", id);
        CommandeForm commandeForm = commandeFormService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(commandeForm));
    }
}
