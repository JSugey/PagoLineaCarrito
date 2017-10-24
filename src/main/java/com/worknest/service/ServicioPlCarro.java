/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service;

import com.worknest.domain.PlCarro;

/**
 *
 * @author WorkNest9
 */
public interface ServicioPlCarro {
    //buscar carro por usuario
    PlCarro buscarUsuario(Long idUsuario);
    
    void guardarCarrito(PlCarro carrito);
}
