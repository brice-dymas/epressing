package com.urservices.epressing.repository;

import com.urservices.epressing.domain.Tarif;

import org.springframework.data.jpa.repository.*;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Tarif entity.
 */
@SuppressWarnings("unused")
public interface TarifRepository extends JpaRepository<Tarif,Long> {

    @Query("SELECT t FROM Tarif t WHERE t.operation.id = :idOperation"
            + " and t.produit.id= :idProduit ")
     public Tarif findByOperationAndProduct(@Param("idOperation") Long idOperation,
      @Param("idProduit") Long  idProduit);
}
