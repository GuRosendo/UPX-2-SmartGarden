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
public class VolunteerPlantings{
    private Long id;
    private Users volunteer;
    private HeadInstitutionLands headInstitutionLand;
    private Institutions institution;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt; 
    private LocalDateTime updatedAt; 

    public VolunteerPlantings(Long id, Users volunteer, HeadInstitutionLands headInstitutionLand, Institutions institution, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt){
        this.id = id;
        this.volunteer = volunteer;
        this.headInstitutionLand = headInstitutionLand;
        this.institution = institution;
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

    public Users getVolunteer(){
        return volunteer;
    }

    public void setVolunteer(Users volunteer){
        this.volunteer = volunteer;
    }

    public HeadInstitutionLands getHeadInstitutionLand(){
        return headInstitutionLand;
    }

    public void setHeadInstitutionLand(HeadInstitutionLands headInstitutionLand){
        this.headInstitutionLand = headInstitutionLand;
    }
    
    public Institutions getInstitution(){
        return institution;
    }

    public void setInstitution(Institutions institution){
        this.institution = institution;
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
