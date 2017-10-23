package com.worknest.repository;

import com.worknest.domain.PlCarroHist;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlCarroHist entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlCarroHistRepository extends JpaRepository<PlCarroHist, Long> {

}
