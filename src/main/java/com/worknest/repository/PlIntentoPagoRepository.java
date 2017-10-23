package com.worknest.repository;

import com.worknest.domain.PlIntentoPago;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlIntentoPago entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlIntentoPagoRepository extends JpaRepository<PlIntentoPago, Long> {

}
