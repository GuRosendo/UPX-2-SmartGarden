/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.domain;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class Users{
    private Long id;
    private Addresses userAddress;
    private Institutions institution; 
    private String fullName;
    private String userName;
    private String userEmail;
    private String userPassword;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt; 
    private LocalDateTime updatedAt;
    
    public Users(){
    }

    public Users(Long id, Addresses userAddress, Institutions institution, String fullName, String userName, String userEmail, String userPassword, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt){
        this.id = id;
        this.userAddress = userAddress;
        this.institution = institution;
        this.fullName = fullName;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
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

    public Addresses getUserAddress(){
        return userAddress;
    }

    public void setUserAddress(Addresses userAddress){
        this.userAddress = userAddress;
    }

    public Institutions getInstitution(){
        return institution;
    }

    public void setInstitution(Institutions institution){
        this.institution = institution;
    }

    public String getFullName(){
        return fullName;
    }

    public void setFullName(String fullName){
        this.fullName = fullName;
    }

    public String getUserName(){
        return userName;
    }

    public void setUserName(String userName){
        this.userName = userName;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public void setUserEmail(String userEmail){
        this.userEmail = userEmail;
    }

    public String getUserPassword(){
        return userPassword;
    }

    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
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

    @Override
    public int hashCode(){
        int hash = 7;
        
        hash = 23 * hash + Objects.hashCode(this.id);
        hash = 23 * hash + Objects.hashCode(this.userPassword);
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        
        if(obj == null){
            return false;
        }
        
        if(getClass() != obj.getClass()){
            return false;
        }
        
        final Users other = (Users) obj;
        
        if(!Objects.equals(this.userPassword, other.userPassword)){
            return false;
        }
        
        return Objects.equals(this.id, other.id);
    }
}
