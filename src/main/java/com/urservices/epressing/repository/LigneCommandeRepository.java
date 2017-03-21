package com.urservices.epressing.repository;

import com.urservices.epressing.domain.LigneCommande;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the LigneCommande entity.
 */
@SuppressWarnings("unused")
public interface LigneCommandeRepository extends JpaRepository<LigneCommande,Long> {

}
