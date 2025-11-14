/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.helper.domain;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class ComboItem{
    private long id;
    private String label;

    public ComboItem(long id, String label){
        this.id = id;
        this.label = label;
    }

    public long getId(){
        return id;
    }

    @Override
    public String toString(){
        return label;
    }
}
