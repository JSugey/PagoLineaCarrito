package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.PlParamRespuesta;

import com.worknest.repository.PlParamRespuestaRepository;
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
 * REST controller for managing PlParamRespuesta.
 */
@RestController
@RequestMapping("/api")
public class PlParamRespuestaResource {

    private final Logger log = LoggerFactory.getLogger(PlParamRespuestaResource.class);

    private static final String ENTITY_NAME = "plParamRespuesta";

    private final PlParamRespuestaRepository plParamRespuestaRepository;
    public PlParamRespuestaResource(PlParamRespuestaRepository plParamRespuestaRepository) {
        this.plParamRespuestaRepository = plParamRespuestaRepository;
    }

    /**
     * POST  /pl-param-respuestas : Create a new plParamRespuesta.
     *
     * @param plParamRespuesta the plParamRespuesta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plParamRespuesta, or with status 400 (Bad Request) if the plParamRespuesta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pl-param-respuestas")
    @Timed
    public ResponseEntity<PlParamRespuesta> createPlParamRespuesta(@Valid @RequestBody PlParamRespuesta plParamRespuesta) throws URISyntaxException {
        log.debug("REST request to save PlParamRespuesta : {}", plParamRespuesta);
        if (plParamRespuesta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new plParamRespuesta cannot already have an ID")).body(null);
        }
        PlParamRespuesta result = plParamRespuestaRepository.save(plParamRespuesta);
        return ResponseEntity.created(new URI("/api/pl-param-respuestas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pl-param-respuestas : Updates an existing plParamRespuesta.
     *
     * @param plParamRespuesta the plParamRespuesta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plParamRespuesta,
     * or with status 400 (Bad Request) if the plParamRespuesta is not valid,
     * or with status 500 (Internal Server Error) if the plParamRespuesta couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pl-param-respuestas")
    @Timed
    public ResponseEntity<PlParamRespuesta> updatePlParamRespuesta(@Valid @RequestBody PlParamRespuesta plParamRespuesta) throws URISyntaxException {
        log.debug("REST request to update PlParamRespuesta : {}", plParamRespuesta);
        if (plParamRespuesta.getId() == null) {
            return createPlParamRespuesta(plParamRespuesta);
        }
        PlParamRespuesta result = plParamRespuestaRepository.save(plParamRespuesta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plParamRespuesta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pl-param-respuestas : get all the plParamRespuestas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plParamRespuestas in body
     */
    @GetMapping("/pl-param-respuestas")
    @Timed
    public List<PlParamRespuesta> getAllPlParamRespuestas() {
        log.debug("REST request to get all PlParamRespuestas");
        return plParamRespuestaRepository.findAll();
        }

    /**
     * GET  /pl-param-respuestas/:id : get the "id" plParamRespuesta.
     *
     * @param id the id of the plParamRespuesta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plParamRespuesta, or with status 404 (Not Found)
     */
    @GetMapping("/pl-param-respuestas/{id}")
    @Timed
    public ResponseEntity<PlParamRespuesta> getPlParamRespuesta(@PathVariable Long id) {
        log.debug("REST request to get PlParamRespuesta : {}", id);
        PlParamRespuesta plParamRespuesta = plParamRespuestaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plParamRespuesta));
    }

    /**
     * DELETE  /pl-param-respuestas/:id : delete the "id" plParamRespuesta.
     *
     * @param id the id of the plParamRespuesta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pl-param-respuestas/{id}")
    @Timed
    public ResponseEntity<Void> deletePlParamRespuesta(@PathVariable Long id) {
        log.debug("REST request to delete PlParamRespuesta : {}", id);
        plParamRespuestaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
