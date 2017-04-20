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
import java.net.URI;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/commandeForms")
    @Timed
    public ResponseEntity<CommandeForm> createLigneCommande(@Valid @RequestBody CommandeForm commandeForm) throws URISyntaxException
    {
        log.debug("REST request to save CommandeForm : {}", commandeForm);
        if (commandeForm != null)
        {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new CommandeForm cannot be null")).body(null);
        }
        CommandeForm result = commandeFormService.save(commandeForm);
        return ResponseEntity.created(new URI("/api/commandeForm/" + result.getCommande().getId()))
                .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getCommande().getId().toString()))
                .body(result);
    }
}
