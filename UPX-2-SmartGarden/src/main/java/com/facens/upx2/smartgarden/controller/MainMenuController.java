/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.model.domain.Users;
import com.facens.upx2.smartgarden.view.form.LandsScreen;
import com.facens.upx2.smartgarden.view.form.CropTypesScreen;
import com.facens.upx2.smartgarden.view.form.PlantingsScreen;
import com.facens.upx2.smartgarden.view.form.UsersScreen;
import com.facens.upx2.smartgarden.view.form.VolunteersScreen;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class MainMenuController{
    private final Users users;
            
    public MainMenuController(Users users){
        this.users = users;
    }
    
    public void navigateToLandsScreen(){
        long institutionId = this.users.getInstitution().getId();
        
        LandsScreen landsScreen = new LandsScreen(institutionId);
        landsScreen.setVisible(true);        
    }
    
    public void navigateToPlantingsScreen(){
        long institutionId = this.users.getInstitution().getId();
        
        PlantingsScreen plantingsScreen = new PlantingsScreen(institutionId);
        plantingsScreen.setVisible(true);
    }
    
    public void navigateToCropTypesScreen(){
        long institutionId = this.users.getInstitution().getId();
        
        CropTypesScreen plantingsScreen = new CropTypesScreen(institutionId);
        plantingsScreen.setVisible(true);
    }
    
    public void navigateToUsersScreen(){
        long institutionId = this.users.getInstitution().getId();
        
        UsersScreen usersScreen = new UsersScreen(institutionId);
        usersScreen.setVisible(true);
    }
    
    public void navigateToVolunteersScreen(){
        long institutionId = this.users.getInstitution().getId();
        
        VolunteersScreen volunteerScreen = new VolunteersScreen(institutionId);
        volunteerScreen.setVisible(true);
    }
}
