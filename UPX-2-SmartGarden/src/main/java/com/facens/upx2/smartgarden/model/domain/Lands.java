/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class Lands{
    private Long id;
    private Addresses landAddress;
    private String landName;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt; 
    private LocalDateTime updatedAt;
    
    public Lands(){}

    public Lands(Long id, Addresses landAddress, String landName, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt){
        this.id = id;
        this.landAddress = landAddress;
        this.landName = landName;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Addresses getLandAddress(){
        return landAddress;
    }

    public void setLandAddress(Addresses landAddress){
        this.landAddress = landAddress;
    }

    public String getLandName(){
        return landName;
    }

    public void setLandName(String landName){
        this.landName = landName;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }
    
    public String getCreatedAtFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.createdAt.format(formatter);
    }
    
    public String getCreatedAtHourFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return this.createdAt.format(formatter);
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt(){
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt){
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }
    
    
}
