package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.PlIntentoPago;

import com.worknest.repository.PlIntentoPagoRepository;
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
 * REST controller for managing PlIntentoPago.
 */
@RestController
@RequestMapping("/api")
public class PlIntentoPagoResource {

    private final Logger log = LoggerFactory.getLogger(PlIntentoPagoResource.class);

    private static final String ENTITY_NAME = "plIntentoPago";

    private final PlIntentoPagoRepository plIntentoPagoRepository;
    public PlIntentoPagoResource(PlIntentoPagoRepository plIntentoPagoRepository) {
        this.plIntentoPagoRepository = plIntentoPagoRepository;
    }

    /**
     * POST  /pl-intento-pagos : Create a new plIntentoPago.
     *
     * @param plIntentoPago the plIntentoPago to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plIntentoPago, or with status 400 (Bad Request) if the plIntentoPago has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pl-intento-pagos")
    @Timed
    public ResponseEntity<PlIntentoPago> createPlIntentoPago(@Valid @RequestBody PlIntentoPago plIntentoPago) throws URISyntaxException {
        log.debug("REST request to save PlIntentoPago : {}", plIntentoPago);
        if (plIntentoPago.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new plIntentoPago cannot already have an ID")).body(null);
        }
        PlIntentoPago result = plIntentoPagoRepository.save(plIntentoPago);
        return ResponseEntity.created(new URI("/api/pl-intento-pagos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pl-intento-pagos : Updates an existing plIntentoPago.
     *
     * @param plIntentoPago the plIntentoPago to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plIntentoPago,
     * or with status 400 (Bad Request) if the plIntentoPago is not valid,
     * or with status 500 (Internal Server Error) if the plIntentoPago couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pl-intento-pagos")
    @Timed
    public ResponseEntity<PlIntentoPago> updatePlIntentoPago(@Valid @RequestBody PlIntentoPago plIntentoPago) throws URISyntaxException {
        log.debug("REST request to update PlIntentoPago : {}", plIntentoPago);
        if (plIntentoPago.getId() == null) {
            return createPlIntentoPago(plIntentoPago);
        }
        PlIntentoPago result = plIntentoPagoRepository.save(plIntentoPago);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plIntentoPago.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pl-intento-pagos : get all the plIntentoPagos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plIntentoPagos in body
     */
    @GetMapping("/pl-intento-pagos")
    @Timed
    public List<PlIntentoPago> getAllPlIntentoPagos() {
        log.debug("REST request to get all PlIntentoPagos");
        return plIntentoPagoRepository.findAll();
        }

    /**
     * GET  /pl-intento-pagos/:id : get the "id" plIntentoPago.
     *
     * @param id the id of the plIntentoPago to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plIntentoPago, or with status 404 (Not Found)
     */
    @GetMapping("/pl-intento-pagos/{id}")
    @Timed
    public ResponseEntity<PlIntentoPago> getPlIntentoPago(@PathVariable Long id) {
        log.debug("REST request to get PlIntentoPago : {}", id);
        PlIntentoPago plIntentoPago = plIntentoPagoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plIntentoPago));
    }

    /**
     * DELETE  /pl-intento-pagos/:id : delete the "id" plIntentoPago.
     *
     * @param id the id of the plIntentoPago to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pl-intento-pagos/{id}")
    @Timed
    public ResponseEntity<Void> deletePlIntentoPago(@PathVariable Long id) {
        log.debug("REST request to delete PlIntentoPago : {}", id);
        plIntentoPagoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
