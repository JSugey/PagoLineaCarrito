package com.worknest.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.worknest.domain.PlCarro;
import com.worknest.domain.PlCarroDet;

import com.worknest.repository.PlCarroDetRepository;
import com.worknest.service.ServicioPlCarroDet;
import com.worknest.service.ServicioPlCarro;
import com.worknest.service.dto.AgregarConceptoDTO;
import com.worknest.web.rest.errors.ExceptionAPI;
import com.worknest.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

/**
 * REST controller for managing PlCarroDet.
 */
@RestController
@RequestMapping("/api")
public class PlCarroDetResource {

    private final Logger log = LoggerFactory.getLogger(PlCarroDetResource.class);

    private static final String ENTITY_NAME = "plCarroDet";
    
    @Autowired
    private ServicioPlCarroDet servicioCarroDet;
    
    @Autowired
    private ServicioPlCarro servicioCarro;

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
    
    /**
     * Metodo que agrega un nuevo concepto a la tabla PlCarroDet
     * @param nuevoConcepto datos del concepto a agregar (llave,bimestre inicia y bimestre final)
     * @return ResponseEntity con status 200(ok) 
     * @throws Exception ResponseEntity con status 400 y mensaje de error
     */
    @PostMapping("/pl-carro-det/agregar-concepto")
    @Timed
    public ResponseEntity agegarConcepto(@RequestBody AgregarConceptoDTO nuevoConcepto) throws Exception{
        ResponseEntity respuesta = null;//Respuesta a la petición del cliente
        Map resultado = new HashMap();//Map para generar el JSON con nombre
        
        Long idUsuario = (long)3701; //se optiene del id del usuario logeado
        PlCarro carrito = servicioCarro.buscarUsuario(idUsuario); //se busca el carro del usuario      
        PlCarroDet detallesCarro;
        try{
            servicioCarroDet.guardarCarritoDet(nuevoConcepto,carrito);//guarda el nuevo concepto en el carro
            respuesta= new ResponseEntity("", HttpStatus.OK);
        }catch(ExceptionAPI e){
            resultado.put("respuesta",e.getMessage());
            respuesta= ResponseEntity.status(e.getEstadoHttp()).body(resultado);
        }      
        return respuesta;
    }
    
    /**
     * Método que lista los conceptos que el usuario logeado tiene en su carro
     * @return ResponseEntity con lista de conceptos que se encuentran en el carro del usuario y status 200
     * @throws Exception si el carro se encuentra vacio se devuelve un codigo 400 con un mensaje de error
     */
    @GetMapping("/pl-carro-det/obtener")
    @Timed
    public ResponseEntity obtenerConceptos() throws Exception{
        ResponseEntity respuesta = null;//Respuesta a la petición del cliente
        Map resultado = new HashMap();//Map para generar el JSON con nombre
        
        Long idUsuario= (long)3701;//obtener el usuario logeado
        PlCarro carrito = servicioCarro.buscarUsuario(idUsuario); //se busca el carro del usuario    

        try{
            resultado.put("respuesta",servicioCarroDet.buscarPorCarro(carrito));
            respuesta= new ResponseEntity(resultado, HttpStatus.OK);
        }catch(ExceptionAPI e){
            resultado.put("respuesta",e.getMessage());
            respuesta= ResponseEntity.status(e.getEstadoHttp()).body(resultado);
        }
        return respuesta;
    }
    
    /**
     * Metodo que borra los conceptos segun su llave
     * @param llave puede ser clave catastra o numero de liquidación
     * @return ResponseEntity con status 200 (OK)
     * @throws Exception RsponseEntity con status 400 y mensaje de error
     */
    @DeleteMapping("/pl-carro-de/borrar/{llave}")
    @Timed
    public ResponseEntity borrarConcepto(@PathVariable String llave) throws Exception{
        ResponseEntity respuesta = null;//Respuesta a la petición del cliente
        Map resultado = new HashMap();//Map para generar el JSON con nombre
        
        try{
            servicioCarroDet.borrarConcepto(llave);
            respuesta= new ResponseEntity("", HttpStatus.OK);
        }catch(ExceptionAPI e){
            resultado.put("respuesta",e.getMessage());
            respuesta= ResponseEntity.status(e.getEstadoHttp()).body(resultado);
        }
        return respuesta;
    }
    
    
}
