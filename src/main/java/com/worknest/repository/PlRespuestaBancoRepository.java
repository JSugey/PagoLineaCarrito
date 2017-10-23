package com.worknest.repository;

import com.worknest.domain.PlRespuestaBanco;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlRespuestaBanco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlRespuestaBancoRepository extends JpaRepository<PlRespuestaBanco, Long> {

}
