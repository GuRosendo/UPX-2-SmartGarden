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
public class CropTypes{
    private Long id;
    private String name;
    private LocalDateTime seedingDateStart;
    private LocalDateTime seedingDateEnd;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt; 
    private LocalDateTime updatedAt;

    public CropTypes(Long id, String name, LocalDateTime seedingDateStart, LocalDateTime seedingDateEnd, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt){
        this.id = id;
        this.name = name;
        this.seedingDateStart = seedingDateStart;
        this.seedingDateEnd = seedingDateEnd;
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

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public LocalDateTime getSeedingDateStart(){
        return seedingDateStart;
    }

    public void setSeedingDateStart(LocalDateTime seedingDateStart){
        this.seedingDateStart = seedingDateStart;
    }

    public LocalDateTime getSeedingDateEnd(){
        return seedingDateEnd;
    }

    public void setSeedingDateEnd(LocalDateTime seedingDateEnd){
        this.seedingDateEnd = seedingDateEnd;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
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
