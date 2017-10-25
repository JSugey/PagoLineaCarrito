package com.worknest.repository;

import com.worknest.domain.PlCarroDet;
import com.worknest.domain.PlCarro;
import java.util.List;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the PlCarroDet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlCarroDetRepository extends JpaRepository<PlCarroDet, Long> {
    List<PlCarroDet> findByCarro(PlCarro idCarro);
    
    List<PlCarroDet> findByLlave(String llave);
}
