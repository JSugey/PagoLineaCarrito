/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service;

import com.worknest.domain.PlCarroDet;
import com.worknest.domain.PlCarroHist;
import com.worknest.web.rest.errors.ExceptionAPI;
import java.util.List;

/**
 *
 * @author WorkNest9
 */
public interface ServicioPlCarroDetHist {
    void guardarConceptos(List<PlCarroDet> listaConceptos, PlCarroHist carroHist) throws ExceptionAPI, Exception;
}
