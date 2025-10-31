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
public class Plantings{
    private Long id;
    private HeadInstitutionLands headInstitutionLand;
    private CropTypes cropType;
    private LocalDateTime approximateHarvestDate;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt; 
    private LocalDateTime updatedAt;

    public Plantings(Long id, HeadInstitutionLands headInstitutionLand, CropTypes cropType, LocalDateTime approximateHarvestDate, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt){
        this.id = id;
        this.headInstitutionLand = headInstitutionLand;
        this.cropType = cropType;
        this.approximateHarvestDate = approximateHarvestDate;
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

    public CropTypes getCropType(){
        return cropType;
    }

    public void setCropType(CropTypes cropType){
        this.cropType = cropType;
    }

    public LocalDateTime getApproximateHarvestDate(){
        return approximateHarvestDate;
    }

    public void setApproximateHarvestDate(LocalDateTime approximateHarvestDate){
        this.approximateHarvestDate = approximateHarvestDate;
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
