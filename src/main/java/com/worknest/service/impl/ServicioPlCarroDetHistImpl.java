/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service.impl;

import com.worknest.domain.PlCarroDetHist;
import com.worknest.domain.PlCarroDet;
import com.worknest.domain.PlCarroHist;
import com.worknest.repository.PlCarroDetHistRepository;
import com.worknest.repository.PlCarroHistRepository;
import com.worknest.service.ServicioPlCarroDetHist;
import com.worknest.web.rest.errors.ExceptionAPI;
import java.time.ZonedDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author WorkNest9
 */
@Service
@Transactional
public class ServicioPlCarroDetHistImpl implements ServicioPlCarroDetHist{

    @Autowired
    private PlCarroDetHistRepository carroDetHistRepositorio;
    
    @Override
    public void guardarConceptos(List<PlCarroDet> listaConceptos, PlCarroHist carroHist)  throws ExceptionAPI, Exception{
        //se revisa si la lista esta vacia 
        if (listaConceptos.isEmpty()) {   
            throw new ExceptionAPI(HttpStatus.BAD_REQUEST,"No se encuentra la lista de conceptos esta vacia");//se arroja una excepcion personalizada con mensaje personalizado
        }
        for (PlCarroDet carroDet : listaConceptos) { //se recorre la lista de conceptos para guardarla en carroDetHist
            //Se guarda el carroDet en un nuevo carroDetHist
            PlCarroDetHist carroDetHist = new PlCarroDetHist(carroDet.getConcepto(),     //se envia el concepto del CarroDet
                                                             carroDet.getIdLiquidacion(),//se envia el id de liquidacion
                                                             carroDet.getFechaVigencia(),//se envia la fecha de vigencia
                                                             carroDet.getImporte(),      //se envia el importe a pagar
                                                             carroDet.getLlave(),        //se envia la llave (clave catastral o numero de liquidacion)
                                                             carroDet.isGeneroUs(),      //se envia si lo genero el usuario o no
                                                             carroHist);                 //se envia el id del carroHist
            carroDetHistRepositorio.save(carroDetHist); //se guarda el carroHist en la base de datos
        }
    }
    
}
