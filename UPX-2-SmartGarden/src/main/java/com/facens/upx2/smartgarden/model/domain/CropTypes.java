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
public class CropTypes{
    private Long id;
    private Institutions institution; 
    private String name;
    private LocalDateTime seedingDateStart;
    private LocalDateTime seedingDateEnd;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt; 
    private LocalDateTime updatedAt;
    
    public CropTypes(){
    }

    public CropTypes(Long id, Institutions institution, String name, LocalDateTime seedingDateStart, LocalDateTime seedingDateEnd, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt){
        this.id = id;
        this.institution = institution;
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
    
    public Institutions getInstitution(){
        return institution;
    }

    public void setInstitution(Institutions institution){
        this.institution = institution;
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
    
    public String getSeedingDateStartFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.seedingDateStart.format(formatter);
    }

    public void setSeedingDateStart(LocalDateTime seedingDateStart){
        this.seedingDateStart = seedingDateStart;
    }

    public LocalDateTime getSeedingDateEnd(){
        return seedingDateEnd;
    }
    
    public String getSeedingDateEndFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.seedingDateEnd.format(formatter);
    }

    public void setSeedingDateEnd(LocalDateTime seedingDateEnd){
        this.seedingDateEnd = seedingDateEnd;
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
