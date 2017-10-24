/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.worknest.service.dto;

/**
 *
 * @author WorkNest9
 */
public class AgregarConceptoDTO {
    private String llave;
    
    private Integer bimini;
    
    private Integer bimfin;

    public AgregarConceptoDTO() {
    }

    public AgregarConceptoDTO(String llave, Integer bimini, Integer bimfin) {
        this.llave = llave;
        this.bimini = bimini;
        this.bimfin = bimfin;
    }

    public String getLlave() {
        return llave;
    }

    public void setLlave(String llave) {
        this.llave = llave;
    }

    public Integer getBimini() {
        return bimini;
    }

    public void setBimini(Integer bimini) {
        this.bimini = bimini;
    }

    public Integer getBimfin() {
        return bimfin;
    }

    public void setBimfin(Integer bimfin) {
        this.bimfin = bimfin;
    }
    
    
}
