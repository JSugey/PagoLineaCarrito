package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.PlParamBanco;

import com.worknest.repository.PlParamBancoRepository;
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
 * REST controller for managing PlParamBanco.
 */
@RestController
@RequestMapping("/api")
public class PlParamBancoResource {

    private final Logger log = LoggerFactory.getLogger(PlParamBancoResource.class);

    private static final String ENTITY_NAME = "plParamBanco";

    private final PlParamBancoRepository plParamBancoRepository;
    public PlParamBancoResource(PlParamBancoRepository plParamBancoRepository) {
        this.plParamBancoRepository = plParamBancoRepository;
    }

    /**
     * POST  /pl-param-bancos : Create a new plParamBanco.
     *
     * @param plParamBanco the plParamBanco to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plParamBanco, or with status 400 (Bad Request) if the plParamBanco has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pl-param-bancos")
    @Timed
    public ResponseEntity<PlParamBanco> createPlParamBanco(@Valid @RequestBody PlParamBanco plParamBanco) throws URISyntaxException {
        log.debug("REST request to save PlParamBanco : {}", plParamBanco);
        if (plParamBanco.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new plParamBanco cannot already have an ID")).body(null);
        }
        PlParamBanco result = plParamBancoRepository.save(plParamBanco);
        return ResponseEntity.created(new URI("/api/pl-param-bancos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pl-param-bancos : Updates an existing plParamBanco.
     *
     * @param plParamBanco the plParamBanco to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plParamBanco,
     * or with status 400 (Bad Request) if the plParamBanco is not valid,
     * or with status 500 (Internal Server Error) if the plParamBanco couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pl-param-bancos")
    @Timed
    public ResponseEntity<PlParamBanco> updatePlParamBanco(@Valid @RequestBody PlParamBanco plParamBanco) throws URISyntaxException {
        log.debug("REST request to update PlParamBanco : {}", plParamBanco);
        if (plParamBanco.getId() == null) {
            return createPlParamBanco(plParamBanco);
        }
        PlParamBanco result = plParamBancoRepository.save(plParamBanco);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plParamBanco.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pl-param-bancos : get all the plParamBancos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plParamBancos in body
     */
    @GetMapping("/pl-param-bancos")
    @Timed
    public List<PlParamBanco> getAllPlParamBancos() {
        log.debug("REST request to get all PlParamBancos");
        return plParamBancoRepository.findAll();
        }

    /**
     * GET  /pl-param-bancos/:id : get the "id" plParamBanco.
     *
     * @param id the id of the plParamBanco to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plParamBanco, or with status 404 (Not Found)
     */
    @GetMapping("/pl-param-bancos/{id}")
    @Timed
    public ResponseEntity<PlParamBanco> getPlParamBanco(@PathVariable Long id) {
        log.debug("REST request to get PlParamBanco : {}", id);
        PlParamBanco plParamBanco = plParamBancoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plParamBanco));
    }

    /**
     * DELETE  /pl-param-bancos/:id : delete the "id" plParamBanco.
     *
     * @param id the id of the plParamBanco to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pl-param-bancos/{id}")
    @Timed
    public ResponseEntity<Void> deletePlParamBanco(@PathVariable Long id) {
        log.debug("REST request to delete PlParamBanco : {}", id);
        plParamBancoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
