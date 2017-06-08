package com.urservices.epressing.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Commande.
 */
@Entity
@Table(name = "commande")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "commande")
public class Commande implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_commande")
    private ZonedDateTime dateCommande;

    @Column(name = "date_facture")
    private ZonedDateTime dateFacture;

    @Column(name = "date_facturation")
    private ZonedDateTime dateFacturation;

    @NotNull
    @Column(name = "date_cueillette", nullable = false)
    private ZonedDateTime dateCueillette;

    @NotNull
    @Column(name = "date_livraison", nullable = false)
    private ZonedDateTime dateLivraison;

    @Column(name = "net_a_payer", precision=10, scale=2)
    private BigDecimal netAPayer;

    @Column(name = "etat")
    private String etat;

    @NotNull
    @Column(name = "adresse_cueillette", nullable = false)
    private String adresseCueillette;

    @NotNull
    @Column(name = "adresse_livraison", nullable = false)
    private String adresseLivraison;

    @Column(name = "adresse_facturation")
    private String adresseFacturation;

    @ManyToOne
    private CarteBancaire carteBancaire;

    @ManyToOne(optional = false)
    @NotNull
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDateCommande() {
        return dateCommande;
    }

    public Commande dateCommande(ZonedDateTime dateCommande) {
        this.dateCommande = dateCommande;
        return this;
    }

    public void setDateCommande(ZonedDateTime dateCommande) {
        this.dateCommande = dateCommande;
    }

    public ZonedDateTime getDateFacture() {
        return dateFacture;
    }

    public Commande dateFacture(ZonedDateTime dateFacture) {
        this.dateFacture = dateFacture;
        return this;
    }

    public void setDateFacture(ZonedDateTime dateFacture) {
        this.dateFacture = dateFacture;
    }

    public ZonedDateTime getDateFacturation() {
        return dateFacturation;
    }

    public Commande dateFacturation(ZonedDateTime dateFacturation) {
        this.dateFacturation = dateFacturation;
        return this;
    }

    public void setDateFacturation(ZonedDateTime dateFacturation) {
        this.dateFacturation = dateFacturation;
    }

    public ZonedDateTime getDateCueillette() {
        return dateCueillette;
    }

    public Commande dateCueillette(ZonedDateTime dateCueillette) {
        this.dateCueillette = dateCueillette;
        return this;
    }

    public void setDateCueillette(ZonedDateTime dateCueillette) {
        this.dateCueillette = dateCueillette;
    }

    public ZonedDateTime getDateLivraison() {
        return dateLivraison;
    }

    public Commande dateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
        return this;
    }

    public void setDateLivraison(ZonedDateTime dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    public BigDecimal getNetAPayer() {
        return netAPayer;
    }

    public Commande netAPayer(BigDecimal netAPayer) {
        this.netAPayer = netAPayer;
        return this;
    }

    public void setNetAPayer(BigDecimal netAPayer) {
        this.netAPayer = netAPayer;
    }

    public String getEtat() {
        return etat;
    }

    public Commande etat(String etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getAdresseCueillette() {
        return adresseCueillette;
    }

    public Commande adresseCueillette(String adresseCueillette) {
        this.adresseCueillette = adresseCueillette;
        return this;
    }

    public void setAdresseCueillette(String adresseCueillette) {
        this.adresseCueillette = adresseCueillette;
    }

    public String getAdresseLivraison() {
        return adresseLivraison;
    }

    public Commande adresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
        return this;
    }

    public void setAdresseLivraison(String adresseLivraison) {
        this.adresseLivraison = adresseLivraison;
    }

    public String getAdresseFacturation() {
        return adresseFacturation;
    }

    public Commande adresseFacturation(String adresseFacturation) {
        this.adresseFacturation = adresseFacturation;
        return this;
    }

    public void setAdresseFacturation(String adresseFacturation) {
        this.adresseFacturation = adresseFacturation;
    }

    public CarteBancaire getCarteBancaire() {
        return carteBancaire;
    }

    public Commande carteBancaire(CarteBancaire carteBancaire) {
        this.carteBancaire = carteBancaire;
        return this;
    }

    public void setCarteBancaire(CarteBancaire carteBancaire) {
        this.carteBancaire = carteBancaire;
    }

    public User getUser() {
        return user;
    }

    public Commande user(User user) {
        this.user = user;
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Commande commande = (Commande) o;
        if (commande.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, commande.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Commande{" +
            "id=" + id +
            ", dateCommande='" + dateCommande + "'" +
            ", dateFacture='" + dateFacture + "'" +
            ", dateFacturation='" + dateFacturation + "'" +
            ", dateCueillette='" + dateCueillette + "'" +
            ", dateLivraison='" + dateLivraison + "'" +
            ", netAPayer='" + netAPayer + "'" +
            ", etat='" + etat + "'" +
            ", adresseCueillette='" + adresseCueillette + "'" +
            ", adresseLivraison='" + adresseLivraison + "'" +
            ", adresseFacturation='" + adresseFacturation + "'" +
            '}';
    }
}
