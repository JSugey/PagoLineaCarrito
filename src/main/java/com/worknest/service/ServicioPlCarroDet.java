/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service;

import com.worknest.domain.PlCarro;
import com.worknest.domain.PlCarroDet;
import com.worknest.service.dto.AgregarConceptoDTO;
import com.worknest.web.rest.errors.ExceptionAPI;
import java.util.List;

/**
 *
 * @author WorkNest9
 */
public interface ServicioPlCarroDet {
    //guarda en el carro los detalles del un nuevo concepto
    void guardarCarritoDet(AgregarConceptoDTO nuevoConcepto, PlCarro carro) throws ExceptionAPI, Exception;
    
    //obtiene los conceptos de un carro
    List<PlCarroDet> buscarPorCarro(PlCarro carro) throws ExceptionAPI, Exception;
    
    //borra concepto por llave
    void borrarConcepto(String llave) throws ExceptionAPI, Exception;
}
