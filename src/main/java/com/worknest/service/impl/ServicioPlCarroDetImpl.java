/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service.impl;

import com.worknest.domain.PlCarroDet;
import com.worknest.domain.PlCarro;
import com.worknest.repository.PlCarroDetRepository;
import com.worknest.service.ServicioPlCarroDet;
import com.worknest.service.dto.AgregarConceptoDTO;
import com.worknest.web.rest.errors.ExceptionAPI;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author WorkNest9
 */
@Service
@Transactional
public class ServicioPlCarroDetImpl implements ServicioPlCarroDet{
    @Autowired
    private PlCarroDetRepository repositorioCarroDet;
    
    @Override
    public void guardarCarritoDet(AgregarConceptoDTO nuevoConcepto, PlCarro carrito) throws ExceptionAPI, Exception {   
        //****datos de prueba***
        Long idLiq = (long)1234;//id liquidacion
        LocalDate fecha = LocalDate.now();//fecha de vigencia
        BigDecimal importe= new BigDecimal("222221.23");//importe
        String concepto;//concepto de liquidacion
        Boolean genero;// si lo genero el usuario
        if(nuevoConcepto.getLlave()==null){
            throw new ExceptionAPI(HttpStatus.BAD_REQUEST,"No se encuentra la llave del concepto");//se arroja una excepcion personalizada con mensaje personalizado
        }
        if (nuevoConcepto.getBimini()==null || nuevoConcepto.getBimfin()== null) {//agregar al carro por numero de liquidacion
            //consultar liquidacion 
            concepto = "concepto prueba liquidacion";
            //crear nuevo concepto en PlCarroDet
            genero=false;    
        }else{ //agregar al carro por clave catastral
            //generar liquidacion con los datos del nuevo concepto
            concepto = "concepto prueba clave catastral";
            //crear nuevo concepto en PlCarroDet
            genero=true;
        }
        PlCarroDet detallesCarro = new PlCarroDet(idLiq, fecha, importe, nuevoConcepto.getLlave(),concepto,genero,carrito); 
        repositorioCarroDet.save(detallesCarro);// se guarda el nuevo concepto en la tabla
    }

    @Override
    public List<PlCarroDet> buscarPorCarro(PlCarro carro)  throws ExceptionAPI, Exception{
        List<PlCarroDet> listaCarro = repositorioCarroDet.findByCarro(carro); //se buscan los conceptos segun el carro del usuario1
        if(listaCarro.isEmpty()){
            throw new ExceptionAPI(HttpStatus.BAD_REQUEST,"No se encontraron conceptos dentro del carro");//se arroja una excepcion personalizada con mensaje personalizado
        }
        return listaCarro;
    }    

    @Override
    public void borrarConcepto(String llave) throws ExceptionAPI, Exception {
        List<PlCarroDet> concepto = repositorioCarroDet.findByLlave(llave);   // Se busca el concepto que se borrara
        if (concepto.isEmpty()) {                                               //si no se encuentra se arroja una excepcion
            throw new ExceptionAPI(HttpStatus.BAD_REQUEST,"No se encuentra la llave del concepto");//se arroja una excepcion personalizada con mensaje personalizado
        }
        for (PlCarroDet carroDet : concepto) {
            if (carroDet.isGeneroUs()) {//si fue generada por el usuario
                //Borrar todas las liquidaciones segun la llave
            }
        }
        repositorioCarroDet.delete(concepto); //se borran los conceptos del a base de datos
    }
}
