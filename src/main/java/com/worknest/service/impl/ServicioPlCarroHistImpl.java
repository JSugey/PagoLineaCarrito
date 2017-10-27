/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service.impl;

import com.worknest.domain.PlCarro;
import com.worknest.domain.PlCarroHist;
import com.worknest.repository.PlCarroHistRepository;
import com.worknest.service.ServicioPlCarroHist;
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
public class ServicioPlCarroHistImpl implements ServicioPlCarroHist {

    @Autowired
    private PlCarroHistRepository carroHistRepositorio;    
    
    @Override
    public PlCarroHist crearCarroHist(PlCarro carro) throws ExceptionAPI, Exception {
        //se revisa que se le envie un carro
        if (carro==null) {   
            throw new ExceptionAPI(HttpStatus.BAD_REQUEST,"No se encuentra el carro del usuario");//se arroja una excepcion personalizada con mensaje personalizado
        }
        //Se crea un nuevo historial de carro (carroHist) con la fecha actual y la referencia vacia
        PlCarroHist carroHist = new PlCarroHist(ZonedDateTime.now(),"",carro);
        carroHistRepositorio.save(carroHist); //se guarda el carroHist en la base de datos
        return carroHist;
    }
    
}
