/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.urservices.epressing.service.impl;

import com.urservices.epressing.domain.Caracteristique;
import com.urservices.epressing.domain.Commande;
import com.urservices.epressing.domain.LigneCommande;
import com.urservices.epressing.domain.CommandeForm;
import com.urservices.epressing.repository.CaracteristiqueRepository;
import com.urservices.epressing.repository.CommandeRepository;
import com.urservices.epressing.repository.LigneCommandeRepository;
import com.urservices.epressing.repository.OperationRepository;
import com.urservices.epressing.repository.ProduitRepository;
import com.urservices.epressing.service.commandeFormService;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author sando
 */
@Service
@Transactional
public class CommandeFormServiceImpl implements commandeFormService
{

    private final Logger log = LoggerFactory.getLogger(LigneCommandeServiceImpl.class);

    private final LigneCommandeRepository ligneCommandeRepository;
    private final CommandeRepository commandeRepository;
    private final CaracteristiqueRepository caracteristiqueRepository;
    private final OperationRepository operationRepository;
    private final ProduitRepository produitRepository;
    private List<LigneCommande> ligneCommandes = new ArrayList<>();

    public CommandeFormServiceImpl(LigneCommandeRepository ligneCommandeRepository, CommandeRepository commandeRepository, CaracteristiqueRepository caracteristiqueRepository, OperationRepository operationRepository, ProduitRepository produitRepository)
    {
        this.ligneCommandeRepository = ligneCommandeRepository;
        this.commandeRepository = commandeRepository;
        this.caracteristiqueRepository = caracteristiqueRepository;
        this.operationRepository = operationRepository;
        this.produitRepository = produitRepository;
    }

    @Override
    public CommandeForm save(CommandeForm commandeForm)
    {
        CommandeForm result = new CommandeForm();
        Caracteristique caracteristique = new Caracteristique();
        Commande commande = commandeRepository.save(commandeForm.getCommande());
        result.setCommande(commande);
//        ligneCommandes = CommandeForm.getLigneCommandes();
        for (LigneCommande ligneCommande : commandeForm.getLigneCommandes())
        {
            ligneCommande.setCommande(commande);
            ligneCommande.setCaracteristique(caracteristiqueRepository.save(ligneCommande.getCaracteristique()));
            ligneCommande.setOperation(operationRepository.findOne(ligneCommande.getOperation().getId()));
            ligneCommande.setProduit(produitRepository.findOne(ligneCommande.getProduit().getId()));
            ligneCommandes.add(ligneCommandeRepository.save(ligneCommande));
        }
        result.setLigneCommandes(ligneCommandes);
        return result;
    }

    @Override
    public CommandeForm findAll(Commande commande)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Long id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
