package com.worknest.repository;

import com.worknest.domain.PlCarro;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlCarro entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlCarroRepository extends JpaRepository<PlCarro, Long> {

}
