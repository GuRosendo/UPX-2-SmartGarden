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
public class Institutions{
    private Long id;
    private Addresses institutionAddress;
    private String institutionName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime deletedAt;

    public Institutions(Long id, Addresses institutionAddress, String institutionName, LocalDateTime createdAt, LocalDateTime updatedAt, LocalDateTime deletedAt){
        this.id = id;
        this.institutionAddress = institutionAddress;
        this.institutionName = institutionName;
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

    public Addresses getInstitutionAddress(){
        return institutionAddress;
    }

    public void setInstitutionAddress(Addresses institutionAddress){
        this.institutionAddress = institutionAddress;
    }

    public String getInstitutionName(){
        return institutionName;
    }

    public void setInstitutionName(String institutionName){
        this.institutionName = institutionName;
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
