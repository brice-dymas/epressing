package com.urservices.epressing.repository;

import com.urservices.epressing.domain.TypeUtilisateur;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TypeUtilisateur entity.
 */
@SuppressWarnings("unused")
public interface TypeUtilisateurRepository extends JpaRepository<TypeUtilisateur,Long> {

}
