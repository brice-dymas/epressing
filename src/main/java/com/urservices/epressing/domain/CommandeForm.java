/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.urservices.epressing.domain;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sando
 */
public class CommandeForm implements Serializable
{

    Commande commande;
    List<LigneCommande> ligneCommandes;

    public CommandeForm()  
    {
    }

    public Commande getCommande()
    {
        return commande;
    }

    public void setCommande(Commande commande)
    {
        this.commande = commande;
    }

    public List<LigneCommande> getLigneCommandes()
    {
        return ligneCommandes;
    }

    public void setLigneCommandes(List<LigneCommande> ligneCommandes)
    {
        this.ligneCommandes = ligneCommandes;
    }

    @Override
    public String toString()
    {
        return "commandeForm{" + "commande=" + commande + ", ligneCommandes=" + ligneCommandes + '}';
    }

}
