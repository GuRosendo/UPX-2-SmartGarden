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
public class HeadInstitutionLands{
    private Long id;
    private Institutions institution;
    private Lands land;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt; 
    private LocalDateTime updatedAt;
    
    public HeadInstitutionLands(){
    }

    public HeadInstitutionLands(Long id, Institutions institution, Lands land, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt){
        this.id = id;
        this.institution = institution;
        this.land = land;
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

    public Lands getLand(){
        return land;
    }

    public void setLand(Lands land){
        this.land = land;
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
