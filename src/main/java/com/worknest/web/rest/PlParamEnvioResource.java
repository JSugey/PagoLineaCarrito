package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.PlParamEnvio;

import com.worknest.repository.PlParamEnvioRepository;
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
 * REST controller for managing PlParamEnvio.
 */
@RestController
@RequestMapping("/api")
public class PlParamEnvioResource {

    private final Logger log = LoggerFactory.getLogger(PlParamEnvioResource.class);

    private static final String ENTITY_NAME = "plParamEnvio";

    private final PlParamEnvioRepository plParamEnvioRepository;
    public PlParamEnvioResource(PlParamEnvioRepository plParamEnvioRepository) {
        this.plParamEnvioRepository = plParamEnvioRepository;
    }

    /**
     * POST  /pl-param-envios : Create a new plParamEnvio.
     *
     * @param plParamEnvio the plParamEnvio to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plParamEnvio, or with status 400 (Bad Request) if the plParamEnvio has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pl-param-envios")
    @Timed
    public ResponseEntity<PlParamEnvio> createPlParamEnvio(@Valid @RequestBody PlParamEnvio plParamEnvio) throws URISyntaxException {
        log.debug("REST request to save PlParamEnvio : {}", plParamEnvio);
        if (plParamEnvio.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new plParamEnvio cannot already have an ID")).body(null);
        }
        PlParamEnvio result = plParamEnvioRepository.save(plParamEnvio);
        return ResponseEntity.created(new URI("/api/pl-param-envios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pl-param-envios : Updates an existing plParamEnvio.
     *
     * @param plParamEnvio the plParamEnvio to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plParamEnvio,
     * or with status 400 (Bad Request) if the plParamEnvio is not valid,
     * or with status 500 (Internal Server Error) if the plParamEnvio couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pl-param-envios")
    @Timed
    public ResponseEntity<PlParamEnvio> updatePlParamEnvio(@Valid @RequestBody PlParamEnvio plParamEnvio) throws URISyntaxException {
        log.debug("REST request to update PlParamEnvio : {}", plParamEnvio);
        if (plParamEnvio.getId() == null) {
            return createPlParamEnvio(plParamEnvio);
        }
        PlParamEnvio result = plParamEnvioRepository.save(plParamEnvio);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plParamEnvio.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pl-param-envios : get all the plParamEnvios.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plParamEnvios in body
     */
    @GetMapping("/pl-param-envios")
    @Timed
    public List<PlParamEnvio> getAllPlParamEnvios() {
        log.debug("REST request to get all PlParamEnvios");
        return plParamEnvioRepository.findAll();
        }

    /**
     * GET  /pl-param-envios/:id : get the "id" plParamEnvio.
     *
     * @param id the id of the plParamEnvio to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plParamEnvio, or with status 404 (Not Found)
     */
    @GetMapping("/pl-param-envios/{id}")
    @Timed
    public ResponseEntity<PlParamEnvio> getPlParamEnvio(@PathVariable Long id) {
        log.debug("REST request to get PlParamEnvio : {}", id);
        PlParamEnvio plParamEnvio = plParamEnvioRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plParamEnvio));
    }

    /**
     * DELETE  /pl-param-envios/:id : delete the "id" plParamEnvio.
     *
     * @param id the id of the plParamEnvio to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pl-param-envios/{id}")
    @Timed
    public ResponseEntity<Void> deletePlParamEnvio(@PathVariable Long id) {
        log.debug("REST request to delete PlParamEnvio : {}", id);
        plParamEnvioRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
