package com.urservices.epressing.service.dto;

import com.urservices.epressing.domain.Produit;
import com.urservices.epressing.domain.Tarif;
import java.util.List;;

/**
 * A DTO representing a user, with his authorities.
 */
public class ProduitTarifDTO {

    private Produit produit;
    private List<Tarif> tarifs;

    public ProduitTarifDTO() { }

    public Produit getProduit(){
        return this.produit;
    }

    public void setProduit(Produit produit){
        this.produit = produit;
    }
    
    public List<Tarif> getTarifs(){
        return this.tarifs;
    }
    public void setTarifs(List<Tarif> tarifs){
        this.tarifs = tarifs;
    }
}
