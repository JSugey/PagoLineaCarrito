package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.PlCarroDetHist;

import com.worknest.repository.PlCarroDetHistRepository;
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
 * REST controller for managing PlCarroDetHist.
 */
@RestController
@RequestMapping("/api")
public class PlCarroDetHistResource {

    private final Logger log = LoggerFactory.getLogger(PlCarroDetHistResource.class);

    private static final String ENTITY_NAME = "plCarroDetHist";

    private final PlCarroDetHistRepository plCarroDetHistRepository;
    public PlCarroDetHistResource(PlCarroDetHistRepository plCarroDetHistRepository) {
        this.plCarroDetHistRepository = plCarroDetHistRepository;
    }

    /**
     * POST  /pl-carro-det-hists : Create a new plCarroDetHist.
     *
     * @param plCarroDetHist the plCarroDetHist to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plCarroDetHist, or with status 400 (Bad Request) if the plCarroDetHist has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pl-carro-det-hists")
    @Timed
    public ResponseEntity<PlCarroDetHist> createPlCarroDetHist(@Valid @RequestBody PlCarroDetHist plCarroDetHist) throws URISyntaxException {
        log.debug("REST request to save PlCarroDetHist : {}", plCarroDetHist);
        if (plCarroDetHist.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new plCarroDetHist cannot already have an ID")).body(null);
        }
        PlCarroDetHist result = plCarroDetHistRepository.save(plCarroDetHist);
        return ResponseEntity.created(new URI("/api/pl-carro-det-hists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pl-carro-det-hists : Updates an existing plCarroDetHist.
     *
     * @param plCarroDetHist the plCarroDetHist to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plCarroDetHist,
     * or with status 400 (Bad Request) if the plCarroDetHist is not valid,
     * or with status 500 (Internal Server Error) if the plCarroDetHist couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pl-carro-det-hists")
    @Timed
    public ResponseEntity<PlCarroDetHist> updatePlCarroDetHist(@Valid @RequestBody PlCarroDetHist plCarroDetHist) throws URISyntaxException {
        log.debug("REST request to update PlCarroDetHist : {}", plCarroDetHist);
        if (plCarroDetHist.getId() == null) {
            return createPlCarroDetHist(plCarroDetHist);
        }
        PlCarroDetHist result = plCarroDetHistRepository.save(plCarroDetHist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plCarroDetHist.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pl-carro-det-hists : get all the plCarroDetHists.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plCarroDetHists in body
     */
    @GetMapping("/pl-carro-det-hists")
    @Timed
    public List<PlCarroDetHist> getAllPlCarroDetHists() {
        log.debug("REST request to get all PlCarroDetHists");
        return plCarroDetHistRepository.findAll();
        }

    /**
     * GET  /pl-carro-det-hists/:id : get the "id" plCarroDetHist.
     *
     * @param id the id of the plCarroDetHist to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plCarroDetHist, or with status 404 (Not Found)
     */
    @GetMapping("/pl-carro-det-hists/{id}")
    @Timed
    public ResponseEntity<PlCarroDetHist> getPlCarroDetHist(@PathVariable Long id) {
        log.debug("REST request to get PlCarroDetHist : {}", id);
        PlCarroDetHist plCarroDetHist = plCarroDetHistRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plCarroDetHist));
    }

    /**
     * DELETE  /pl-carro-det-hists/:id : delete the "id" plCarroDetHist.
     *
     * @param id the id of the plCarroDetHist to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pl-carro-det-hists/{id}")
    @Timed
    public ResponseEntity<Void> deletePlCarroDetHist(@PathVariable Long id) {
        log.debug("REST request to delete PlCarroDetHist : {}", id);
        plCarroDetHistRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
