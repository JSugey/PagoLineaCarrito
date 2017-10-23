package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.PlCarroDet;

import com.worknest.repository.PlCarroDetRepository;
import com.worknest.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing PlCarroDet.
 */
@RestController
@RequestMapping("/api")
public class PlCarroDetResource {

    private final Logger log = LoggerFactory.getLogger(PlCarroDetResource.class);

    private static final String ENTITY_NAME = "plCarroDet";

    private final PlCarroDetRepository plCarroDetRepository;
    public PlCarroDetResource(PlCarroDetRepository plCarroDetRepository) {
        this.plCarroDetRepository = plCarroDetRepository;
    }

    /**
     * POST  /pl-carro-dets : Create a new plCarroDet.
     *
     * @param plCarroDet the plCarroDet to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plCarroDet, or with status 400 (Bad Request) if the plCarroDet has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pl-carro-dets")
    @Timed
    public ResponseEntity<PlCarroDet> createPlCarroDet(@Valid @RequestBody PlCarroDet plCarroDet) throws URISyntaxException {
        log.debug("REST request to save PlCarroDet : {}", plCarroDet);
        if (plCarroDet.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new plCarroDet cannot already have an ID")).body(null);
        }
        PlCarroDet result = plCarroDetRepository.save(plCarroDet);
        return ResponseEntity.created(new URI("/api/pl-carro-dets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pl-carro-dets : Updates an existing plCarroDet.
     *
     * @param plCarroDet the plCarroDet to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plCarroDet,
     * or with status 400 (Bad Request) if the plCarroDet is not valid,
     * or with status 500 (Internal Server Error) if the plCarroDet couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pl-carro-dets")
    @Timed
    public ResponseEntity<PlCarroDet> updatePlCarroDet(@Valid @RequestBody PlCarroDet plCarroDet) throws URISyntaxException {
        log.debug("REST request to update PlCarroDet : {}", plCarroDet);
        if (plCarroDet.getId() == null) {
            return createPlCarroDet(plCarroDet);
        }
        PlCarroDet result = plCarroDetRepository.save(plCarroDet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plCarroDet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pl-carro-dets : get all the plCarroDets.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plCarroDets in body
     */
    @GetMapping("/pl-carro-dets")
    @Timed
    public List<PlCarroDet> getAllPlCarroDets() {
        log.debug("REST request to get all PlCarroDets");
        return plCarroDetRepository.findAll();
        }

    /**
     * GET  /pl-carro-dets/:id : get the "id" plCarroDet.
     *
     * @param id the id of the plCarroDet to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plCarroDet, or with status 404 (Not Found)
     */
    @GetMapping("/pl-carro-dets/{id}")
    @Timed
    public ResponseEntity<PlCarroDet> getPlCarroDet(@PathVariable Long id) {
        log.debug("REST request to get PlCarroDet : {}", id);
        PlCarroDet plCarroDet = plCarroDetRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plCarroDet));
    }

    /**
     * DELETE  /pl-carro-dets/:id : delete the "id" plCarroDet.
     *
     * @param id the id of the plCarroDet to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pl-carro-dets/{id}")
    @Timed
    public ResponseEntity<Void> deletePlCarroDet(@PathVariable Long id) {
        log.debug("REST request to delete PlCarroDet : {}", id);
        plCarroDetRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
