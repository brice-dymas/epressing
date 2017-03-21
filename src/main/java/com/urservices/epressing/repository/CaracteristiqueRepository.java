package com.urservices.epressing.repository;

import com.urservices.epressing.domain.Caracteristique;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Caracteristique entity.
 */
@SuppressWarnings("unused")
public interface CaracteristiqueRepository extends JpaRepository<Caracteristique,Long> {

}
