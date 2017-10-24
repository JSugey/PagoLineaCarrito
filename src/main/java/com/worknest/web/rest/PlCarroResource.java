package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.PlCarro;

import com.worknest.repository.PlCarroRepository;
import com.worknest.service.ServicioPlCarro;
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
import org.springframework.beans.factory.annotation.Autowired;

/**
 * REST controller for managing PlCarro.
 */
@RestController
@RequestMapping("/api")
public class PlCarroResource {

    private final Logger log = LoggerFactory.getLogger(PlCarroResource.class);

    private static final String ENTITY_NAME = "plCarro";
    
    @Autowired
    private ServicioPlCarro servicioCarro;

    private final PlCarroRepository plCarroRepository;
    public PlCarroResource(PlCarroRepository plCarroRepository) {
        this.plCarroRepository = plCarroRepository;
    }

    /**
     * POST  /pl-carros : Create a new plCarro.
     *
     * @param plCarro the plCarro to create
     * @return the ResponseEntity with status 201 (Created) and with body the new plCarro, or with status 400 (Bad Request) if the plCarro has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pl-carros")
    @Timed
    public ResponseEntity<PlCarro> createPlCarro(@Valid @RequestBody PlCarro plCarro) throws URISyntaxException {
        log.debug("REST request to save PlCarro : {}", plCarro);
        if (plCarro.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new plCarro cannot already have an ID")).body(null);
        }
        PlCarro result = plCarroRepository.save(plCarro);
        return ResponseEntity.created(new URI("/api/pl-carros/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pl-carros : Updates an existing plCarro.
     *
     * @param plCarro the plCarro to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated plCarro,
     * or with status 400 (Bad Request) if the plCarro is not valid,
     * or with status 500 (Internal Server Error) if the plCarro couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pl-carros")
    @Timed
    public ResponseEntity<PlCarro> updatePlCarro(@Valid @RequestBody PlCarro plCarro) throws URISyntaxException {
        log.debug("REST request to update PlCarro : {}", plCarro);
        if (plCarro.getId() == null) {
            return createPlCarro(plCarro);
        }
        PlCarro result = plCarroRepository.save(plCarro);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, plCarro.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pl-carros : get all the plCarros.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of plCarros in body
     */
    @GetMapping("/pl-carros")
    @Timed
    public List<PlCarro> getAllPlCarros() {
        log.debug("REST request to get all PlCarros");
        return plCarroRepository.findAll();
        }

    /**
     * GET  /pl-carros/:id : get the "id" plCarro.
     *
     * @param id the id of the plCarro to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the plCarro, or with status 404 (Not Found)
     */
    @GetMapping("/pl-carros/{id}")
    @Timed
    public ResponseEntity<PlCarro> getPlCarro(@PathVariable Long id) {
        log.debug("REST request to get PlCarro : {}", id);
        PlCarro plCarro = plCarroRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(plCarro));
    }

    /**
     * DELETE  /pl-carros/:id : delete the "id" plCarro.
     *
     * @param id the id of the plCarro to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pl-carros/{id}")
    @Timed
    public ResponseEntity<Void> deletePlCarro(@PathVariable Long id) {
        log.debug("REST request to delete PlCarro : {}", id);
        plCarroRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
    
    /**
     * Metodo para verificar que el usuario logeado tenga un carro
     * si no lo tiene se le crea uno
     */    
    @PostMapping("pl-carro/crear")
    @Timed
    public void crearCarro(){
        Long idUsuario = (long)951; //se optiene del id del usuario logeado
        PlCarro carrito = servicioCarro.buscarUsuario(idUsuario); //se busca si el usuario tiene un carro registrado
        if (carrito == null) { //si el usuario no tiene un carro, se le crea uno
            PlCarro carrito2 = new PlCarro(idUsuario);            
            servicioCarro.guardarCarrito(carrito2);
        }
    }
    
}
