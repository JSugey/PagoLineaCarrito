/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service.impl;

import com.worknest.domain.PlCarro;
import com.worknest.repository.PlCarroRepository;
import com.worknest.service.ServicioPlCarro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author WorkNest9
 */
@Service
@Transactional
public class ServicioPlCarroImpl implements ServicioPlCarro{
    
    @Autowired
    private PlCarroRepository repositorioCarro;
    
    @Override
    public PlCarro buscarUsuario(Long idUsuario) {
        PlCarro carrito = repositorioCarro.findByIdUsuario(idUsuario);
        return carrito;
    }

    @Override
    public void guardarCarrito(PlCarro carrito) {
        repositorioCarro.save(carrito);
    }
    
}
