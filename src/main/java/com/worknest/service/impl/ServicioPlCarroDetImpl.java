/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service.impl;

import com.worknest.domain.PlCarroDet;
import com.worknest.repository.PlCarroDetRepository;
import com.worknest.service.ServicioPlCarroDet;
import com.worknest.web.rest.errors.ExceptionAPI;
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
public class ServicioPlCarroDetImpl implements ServicioPlCarroDet{
    @Autowired
    private PlCarroDetRepository repositorioCarroDet;
    
    @Override
    public void guardarCarritoDet(PlCarroDet carritoDet) {
        repositorioCarroDet.save(carritoDet);
    }
    
}
