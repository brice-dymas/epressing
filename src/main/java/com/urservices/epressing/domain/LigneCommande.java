package com.urservices.epressing.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * A LigneCommande.
 */
@Entity
@Table(name = "ligne_commande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "lignecommande")
public class LigneCommande implements Serializable
{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "quantite", nullable = false)
    private Integer quantite;

    @NotNull
    @Column(name = "etat", nullable = false)
    private String etat;

    @ManyToOne
    private Produit produit;

    @ManyToOne
    private Commande commande;

    @ManyToOne
    private Operation operation;

    @ManyToOne
    private Caracteristique caracteristique;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Integer getQuantite()
    {
        return quantite;
    }

    public LigneCommande quantite(Integer quantite)
    {
        this.quantite = quantite;
        return this;
    }

    public void setQuantite(Integer quantite)
    {
        this.quantite = quantite;
    }

    public String getEtat()
    {
        return etat;
    }

    public LigneCommande etat(String etat)
    {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat)
    {
        this.etat = etat;
    }

    public Produit getProduit()
    {
        return produit;
    }

    public LigneCommande produit(Produit produit)
    {
        this.produit = produit;
        return this;
    }

    public void setProduit(Produit produit)
    {
        this.produit = produit;
    }

    public Commande getCommande()
    {
        return commande;
    }

    public LigneCommande commande(Commande commande)
    {
        this.commande = commande;
        return this;
    }

    public void setCommande(Commande commande)
    {
        this.commande = commande;
    }

    public Operation getOperation()
    {
        return operation;
    }

    public LigneCommande operation(Operation operation)
    {
        this.operation = operation;
        return this;
    }

    public void setOperation(Operation operation)
    {
        this.operation = operation;
    }

    public Caracteristique getCaracteristique()
    {
        return caracteristique;
    }

    public LigneCommande caracteristique(Caracteristique caracteristique)
    {
        this.caracteristique = caracteristique;
        return this;
    }

    public void setCaracteristique(Caracteristique caracteristique)
    {
        this.caracteristique = caracteristique;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass())
        {
            return false;
        }
        LigneCommande ligneCommande = (LigneCommande) o;
        if (ligneCommande.id == null || id == null)
        {
            return false;
        }
        return Objects.equals(id, ligneCommande.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode(id);
    }

    @Override
    public String toString()
    {
        return "LigneCommande{"
                + "id=" + id
                + ", quantite='" + quantite + "'"
                + ", etat='" + etat + "'"
                + '}';
    }
}
