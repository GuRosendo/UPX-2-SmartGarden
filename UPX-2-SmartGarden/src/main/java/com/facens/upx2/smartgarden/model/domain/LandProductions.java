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
public class LandProductions{
    private Long id;
    private HeadInstitutionLands headInstitutionLand;
    private BigDecimal profit;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt; 
    private LocalDateTime updatedAt;

    public LandProductions(Long id, HeadInstitutionLands headInstitutionLand, BigDecimal profit, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt){
        this.id = id;
        this.headInstitutionLand = headInstitutionLand;
        this.profit = profit;
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

    public BigDecimal getProfit(){
        return profit;
    }

    public void setProfit(BigDecimal profit){
        this.profit = profit;
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
