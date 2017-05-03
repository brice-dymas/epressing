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

    @Query("SELECT T FROM Tarif T WHERE T.operation.id = :idOperation "
            + " AND T.produit.id = :idProduit ")
     public Tarif findByOperationAndProduct(@Param("idOperation") Long idOperation,
      @Param("idProduit") Long  idProduit);

      @Query("SELECT T FROM Tarif T WHERE T.produit.id = :idProduit ")
     public List<Tarif> findByProductID(@Param("idProduit") Long  idProduit);
}   
