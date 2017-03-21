package com.urservices.epressing.repository;

import com.urservices.epressing.domain.Tarif;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Tarif entity.
 */
@SuppressWarnings("unused")
public interface TarifRepository extends JpaRepository<Tarif,Long> {

}
