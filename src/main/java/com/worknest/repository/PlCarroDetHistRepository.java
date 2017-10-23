package com.worknest.repository;

import com.worknest.domain.PlCarroDetHist;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlCarroDetHist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlCarroDetHistRepository extends JpaRepository<PlCarroDetHist, Long> {

}
