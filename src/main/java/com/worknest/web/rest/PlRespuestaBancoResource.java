package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.PlRespuestaBanco;

import com.worknest.repository.PlRespuestaBancoRepository;
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
 * REST controller for managing PlRespuestaBanco.
 */
@RestController
@RequestMapping("/api")
public class PlRespuestaBancoResource {

    private final Logger log = LoggerFactory.getLogger(PlRespuestaBancoResource.class);

    private static final String ENTITY_NAME = "plRespuestaBanco";

    private final PlRespuestaBancoRepository plRespuestaBancoRepository;
    public PlRespuestaBancoResource(PlRespuestaBancoRepository plRespuestaBancoRepository) {
        this.plRespuestaBancoRepository = plRespuestaBancoRepository;
    }

    /**
     * POST  /pl-respuesta-bancos : Create a new plRespuestaBanco.
     *
     * @param plRespuestaBanco the plRespuestaBanco to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plRespuestaBanco, or with status 400 (Bad Request) if the plRespuestaBanco has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pl-respuesta-bancos")
    @Timed
    public ResponseEntity<PlRespuestaBanco> createPlRespuestaBanco(@Valid @RequestBody PlRespuestaBanco plRespuestaBanco) throws URISyntaxException {
        log.debug("REST request to save PlRespuestaBanco : {}", plRespuestaBanco);
        if (plRespuestaBanco.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new plRespuestaBanco cannot already have an ID")).body(null);
        }
        PlRespuestaBanco result = plRespuestaBancoRepository.save(plRespuestaBanco);
        return ResponseEntity.created(new URI("/api/pl-respuesta-bancos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pl-respuesta-bancos : Updates an existing plRespuestaBanco.
     *
     * @param plRespuestaBanco the plRespuestaBanco to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plRespuestaBanco,
     * or with status 400 (Bad Request) if the plRespuestaBanco is not valid,
     * or with status 500 (Internal Server Error) if the plRespuestaBanco couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pl-respuesta-bancos")
    @Timed
    public ResponseEntity<PlRespuestaBanco> updatePlRespuestaBanco(@Valid @RequestBody PlRespuestaBanco plRespuestaBanco) throws URISyntaxException {
        log.debug("REST request to update PlRespuestaBanco : {}", plRespuestaBanco);
        if (plRespuestaBanco.getId() == null) {
            return createPlRespuestaBanco(plRespuestaBanco);
        }
        PlRespuestaBanco result = plRespuestaBancoRepository.save(plRespuestaBanco);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plRespuestaBanco.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pl-respuesta-bancos : get all the plRespuestaBancos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plRespuestaBancos in body
     */
    @GetMapping("/pl-respuesta-bancos")
    @Timed
    public List<PlRespuestaBanco> getAllPlRespuestaBancos() {
        log.debug("REST request to get all PlRespuestaBancos");
        return plRespuestaBancoRepository.findAll();
        }

    /**
     * GET  /pl-respuesta-bancos/:id : get the "id" plRespuestaBanco.
     *
     * @param id the id of the plRespuestaBanco to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plRespuestaBanco, or with status 404 (Not Found)
     */
    @GetMapping("/pl-respuesta-bancos/{id}")
    @Timed
    public ResponseEntity<PlRespuestaBanco> getPlRespuestaBanco(@PathVariable Long id) {
        log.debug("REST request to get PlRespuestaBanco : {}", id);
        PlRespuestaBanco plRespuestaBanco = plRespuestaBancoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plRespuestaBanco));
    }

    /**
     * DELETE  /pl-respuesta-bancos/:id : delete the "id" plRespuestaBanco.
     *
     * @param id the id of the plRespuestaBanco to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pl-respuesta-bancos/{id}")
    @Timed
    public ResponseEntity<Void> deletePlRespuestaBanco(@PathVariable Long id) {
        log.debug("REST request to delete PlRespuestaBanco : {}", id);
        plRespuestaBancoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
