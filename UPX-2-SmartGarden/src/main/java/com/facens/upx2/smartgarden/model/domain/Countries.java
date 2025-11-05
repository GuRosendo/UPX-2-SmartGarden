/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.domain;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class Countries{
    private Long id;
    private String name;
    private String namePt;
    private String acronym;
    private int bacen;
    
    public Countries(){
    }

    public Countries(Long id, String name, String namePt, String acronym, int bacen) {
        this.id = id;
        this.name = name;
        this.namePt = namePt;
        this.acronym = acronym;
        this.bacen = bacen;
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

    public String getNamePt() {
        return namePt;
    }

    public void setNamePt(String name_pt) {
        this.namePt = name_pt;
    }

    public String getAcronym() {
        return acronym;
    }

    public void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public int getBacen() {
        return bacen;
    }

    public void setBacen(int bacen) {
        this.bacen = bacen;
    }
}
