package com.worknest.repository;

import com.worknest.domain.PlParamBanco;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlParamBanco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlParamBancoRepository extends JpaRepository<PlParamBanco, Long> {

}
