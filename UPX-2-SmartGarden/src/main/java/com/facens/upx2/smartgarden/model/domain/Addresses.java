/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.domain;

import java.time.LocalDateTime;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class Addresses{
    private Long id;
    private String CEP;
    private String countryName;
    private String cityName;
    private String neighborhoodName;
    private String streetName;
    private String number;
    private int type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Addresses(Long id, String CEP, String countryName, String cityName, String neighborhoodName, String streetName, String number, int type, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt){
        this.id = id;
        this.CEP = CEP;
        this.countryName = countryName;
        this.cityName = cityName;
        this.neighborhoodName = neighborhoodName;
        this.streetName = streetName;
        this.number = number;
        this.type = type;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    } 

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public String getCEP(){
        return CEP;
    }

    public void setCEP(String CEP){
        this.CEP = CEP;
    }

    public String getCountryName(){
        return countryName;
    }

    public void setCountryName(String countryName){
        this.countryName = countryName;
    }

    public String getCityName(){
        return cityName;
    }

    public void setCityName(String cityName){
        this.cityName = cityName;
    }

    public String getNeighborhoodName(){
        return neighborhoodName;
    }

    public void setNeighborhoodName(String neighborhoodName){
        this.neighborhoodName = neighborhoodName;
    }

    public String getStreetName(){
        return streetName;
    }

    public void setStreetName(String streetName){
        this.streetName = streetName;
    }

    public String getNumber(){
        return number;
    }

    public void setNumber(String number){
        this.number = number;
    }

    public int getType() {
        return type;
    }

    public void setType(int type){
        this.type = type;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getDeletedAt(){
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt){
        this.deletedAt = deletedAt;
    }
    
    
}
