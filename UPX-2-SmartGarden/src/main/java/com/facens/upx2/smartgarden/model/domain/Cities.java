/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.domain;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class Cities{
    private Long id;
    private String name;
    private States uf;
    private int ibge;
    
    public Cities(){
    }

    public Cities(Long id, String name, States uf, int ibge) {
        this.id = id;
        this.name = name;
        this.uf = uf;
        this.ibge = ibge;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public States getUf() {
        return uf;
    }

    public void setUf(States uf) {
        this.uf = uf;
    }

    public int getIbge() {
        return ibge;
    }

    public void setIbge(int ibge) {
        this.ibge = ibge;
    }
}
