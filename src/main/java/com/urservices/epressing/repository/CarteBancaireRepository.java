package com.urservices.epressing.repository;

import com.urservices.epressing.domain.CarteBancaire;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CarteBancaire entity.
 */
@SuppressWarnings("unused")
public interface CarteBancaireRepository extends JpaRepository<CarteBancaire,Long> {

}
