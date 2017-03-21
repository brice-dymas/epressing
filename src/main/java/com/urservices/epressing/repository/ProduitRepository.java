package com.urservices.epressing.repository;

import com.urservices.epressing.domain.Produit;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Produit entity.
 */
@SuppressWarnings("unused")
public interface ProduitRepository extends JpaRepository<Produit,Long> {

}
