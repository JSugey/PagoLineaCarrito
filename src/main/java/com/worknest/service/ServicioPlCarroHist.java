/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service;

import com.worknest.domain.PlCarro;
import com.worknest.domain.PlCarroHist;
import com.worknest.web.rest.errors.ExceptionAPI;

/**
 *
 * @author WorkNest9
 */
public interface ServicioPlCarroHist {
    //crea un nuevo CarroHist en base al carrito del usuario
    PlCarroHist crearCarroHist(PlCarro carro) throws ExceptionAPI, Exception;
}
