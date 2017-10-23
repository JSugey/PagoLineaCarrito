package com.worknest.repository;

import com.worknest.domain.PlParamRespuesta;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlParamRespuesta entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlParamRespuestaRepository extends JpaRepository<PlParamRespuesta, Long> {

}
