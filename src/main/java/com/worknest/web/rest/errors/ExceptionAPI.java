/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.web.rest.errors;

import org.springframework.http.HttpStatus;
/**
 *
 * @author WorkNest9
 */
public class ExceptionAPI extends Exception{

    private String mensaje;
    private HttpStatus estadoHttp;
    
    public ExceptionAPI(HttpStatus estadoHttp,String mensaje){
        super(mensaje);
        this.estadoHttp = estadoHttp;
    }
    
    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the estadoHttp
     */
    public HttpStatus getEstadoHttp() {
        return estadoHttp;
    }

    /**
     * @param estadoHttp the estadoHttp to set
     */
    public void setEstadoHttp(HttpStatus estadoHttp) {
        this.estadoHttp = estadoHttp;
    }
    
   
 
}