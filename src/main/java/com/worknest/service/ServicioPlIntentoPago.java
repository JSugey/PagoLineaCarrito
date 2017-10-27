/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service;

import com.worknest.domain.PlCarroHist;
import com.worknest.web.rest.errors.ExceptionAPI;

/**
 *
 * @author WorkNest9
 */

public interface ServicioPlIntentoPago {
    //se crea un nuevo intento de pago
    void crearIntentoPago(PlCarroHist carroHist) throws ExceptionAPI, Exception;
}
