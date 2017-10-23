package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.PlCarroHist;

import com.worknest.repository.PlCarroHistRepository;
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
 * REST controller for managing PlCarroHist.
 */
@RestController
@RequestMapping("/api")
public class PlCarroHistResource {

    private final Logger log = LoggerFactory.getLogger(PlCarroHistResource.class);

    private static final String ENTITY_NAME = "plCarroHist";

    private final PlCarroHistRepository plCarroHistRepository;
    public PlCarroHistResource(PlCarroHistRepository plCarroHistRepository) {
        this.plCarroHistRepository = plCarroHistRepository;
    }

    /**
     * POST  /pl-carro-hists : Create a new plCarroHist.
     *
     * @param plCarroHist the plCarroHist to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plCarroHist, or with status 400 (Bad Request) if the plCarroHist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pl-carro-hists")
    @Timed
    public ResponseEntity<PlCarroHist> createPlCarroHist(@Valid @RequestBody PlCarroHist plCarroHist) throws URISyntaxException {
        log.debug("REST request to save PlCarroHist : {}", plCarroHist);
        if (plCarroHist.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new plCarroHist cannot already have an ID")).body(null);
        }
        PlCarroHist result = plCarroHistRepository.save(plCarroHist);
        return ResponseEntity.created(new URI("/api/pl-carro-hists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pl-carro-hists : Updates an existing plCarroHist.
     *
     * @param plCarroHist the plCarroHist to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plCarroHist,
     * or with status 400 (Bad Request) if the plCarroHist is not valid,
     * or with status 500 (Internal Server Error) if the plCarroHist couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pl-carro-hists")
    @Timed
    public ResponseEntity<PlCarroHist> updatePlCarroHist(@Valid @RequestBody PlCarroHist plCarroHist) throws URISyntaxException {
        log.debug("REST request to update PlCarroHist : {}", plCarroHist);
        if (plCarroHist.getId() == null) {
            return createPlCarroHist(plCarroHist);
        }
        PlCarroHist result = plCarroHistRepository.save(plCarroHist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plCarroHist.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pl-carro-hists : get all the plCarroHists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plCarroHists in body
     */
    @GetMapping("/pl-carro-hists")
    @Timed
    public List<PlCarroHist> getAllPlCarroHists() {
        log.debug("REST request to get all PlCarroHists");
        return plCarroHistRepository.findAll();
        }

    /**
     * GET  /pl-carro-hists/:id : get the "id" plCarroHist.
     *
     * @param id the id of the plCarroHist to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plCarroHist, or with status 404 (Not Found)
     */
    @GetMapping("/pl-carro-hists/{id}")
    @Timed
    public ResponseEntity<PlCarroHist> getPlCarroHist(@PathVariable Long id) {
        log.debug("REST request to get PlCarroHist : {}", id);
        PlCarroHist plCarroHist = plCarroHistRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plCarroHist));
    }

    /**
     * DELETE  /pl-carro-hists/:id : delete the "id" plCarroHist.
     *
     * @param id the id of the plCarroHist to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pl-carro-hists/{id}")
    @Timed
    public ResponseEntity<Void> deletePlCarroHist(@PathVariable Long id) {
        log.debug("REST request to delete PlCarroHist : {}", id);
        plCarroHistRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
