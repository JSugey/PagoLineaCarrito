/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service.impl;

import com.worknest.domain.PlCarroHist;
import com.worknest.domain.PlIntentoPago;
import com.worknest.domain.enumeration.StatusIntentoPago;
import com.worknest.repository.PlIntentoPagoRepository;
import com.worknest.service.ServicioPlIntentoPago;
import com.worknest.web.rest.errors.ExceptionAPI;
import java.time.ZonedDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author WorkNest9
 */
@Service
@Transactional
public class ServicioPlIntentoPagoImpl implements ServicioPlIntentoPago {

    @Autowired
    private PlIntentoPagoRepository intentoPagoRepositorio;
    
    @Override
    public void crearIntentoPago(PlCarroHist carroHist) throws ExceptionAPI, Exception {
        //se lanza una excepcion si el carroHist viene vacio
        if (carroHist==null) {   
            throw new ExceptionAPI(HttpStatus.BAD_REQUEST,"No se encuentra el historial del carro");//se arroja una excepcion personalizada con mensaje personalizado
        }
        // se crea un nuevo intento de pago
        PlIntentoPago intentoPago = new PlIntentoPago(ZonedDateTime.now(),           //se agrega la fecha en que se genero el intento
                                                        false,                       //se agrega si ya fue enviado al banco          
                                                        StatusIntentoPago.DISPERSA,  //se agrega el status
                                                        "",                          //se agrega la autorización
                                                        carroHist);                  //se envia el carroHist
        intentoPagoRepositorio.save(intentoPago);
    }
    
}
