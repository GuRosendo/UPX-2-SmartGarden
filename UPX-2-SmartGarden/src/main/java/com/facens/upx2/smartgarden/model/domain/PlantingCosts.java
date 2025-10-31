/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class PlantingCosts{
    private Long id;
    private HeadInstitutionLands headInstitutionLand;
    private String title;
    private String description;
    private BigDecimal cost;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt; 
    private LocalDateTime updatedAt; 

    public PlantingCosts(Long id, HeadInstitutionLands headInstitutionLand, String title, String description, BigDecimal cost, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt){
        this.id = id;
        this.headInstitutionLand = headInstitutionLand;
        this.title = title;
        this.description = description;
        this.cost = cost;
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

    public HeadInstitutionLands getHeadInstitutionLand(){
        return headInstitutionLand;
    }

    public void setHeadInstitutionLand(HeadInstitutionLands headInstitutionLand){
        this.headInstitutionLand = headInstitutionLand;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public BigDecimal getCost(){
        return cost;
    }

    public void setCost(BigDecimal cost){
        this.cost = cost;
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
