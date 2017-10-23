package com.worknest.repository;

import com.worknest.domain.PlParamEnvio;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlParamEnvio entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlParamEnvioRepository extends JpaRepository<PlParamEnvio, Long> {

}
