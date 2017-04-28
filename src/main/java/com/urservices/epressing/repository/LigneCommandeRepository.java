package com.urservices.epressing.repository;

import com.urservices.epressing.domain.LigneCommande;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the LigneCommande entity.
 */
@SuppressWarnings("unused")
public interface LigneCommandeRepository extends JpaRepository<LigneCommande,Long> {
    @Query("SELECT L FROM LigneCommande L WHERE L.commande.id = :idCommande ")
     public List<LigneCommande> findByCommandeID(@Param("idCommande") Long idCommande);
}
