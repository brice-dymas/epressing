package com.urservices.epressing.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A CarteBancaire.
 */
@Entity
@Table(name = "carte_bancaire")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "cartebancaire")
public class CarteBancaire implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "numero", nullable = false)
    private String numero;

    @NotNull
    @Column(name = "code_hvc", nullable = false)
    private String codeHVC;

    @NotNull
    @Column(name = "date_expiration", nullable = false)
    private ZonedDateTime dateExpiration;

    @ManyToOne
    private Utilisateur utilisateur;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public CarteBancaire numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCodeHVC() {
        return codeHVC;
    }

    public CarteBancaire codeHVC(String codeHVC) {
        this.codeHVC = codeHVC;
        return this;
    }

    public void setCodeHVC(String codeHVC) {
        this.codeHVC = codeHVC;
    }

    public ZonedDateTime getDateExpiration() {
        return dateExpiration;
    }

    public CarteBancaire dateExpiration(ZonedDateTime dateExpiration) {
        this.dateExpiration = dateExpiration;
        return this;
    }

    public void setDateExpiration(ZonedDateTime dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public CarteBancaire utilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        return this;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CarteBancaire carteBancaire = (CarteBancaire) o;
        if (carteBancaire.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, carteBancaire.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CarteBancaire{" +
            "id=" + id +
            ", numero='" + numero + "'" +
            ", codeHVC='" + codeHVC + "'" +
            ", dateExpiration='" + dateExpiration + "'" +
            '}';
    }
}
