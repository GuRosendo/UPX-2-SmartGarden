/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.model.domain.Users;
import com.facens.upx2.smartgarden.view.form.LandsScreen;
import com.facens.upx2.smartgarden.view.form.MainMenuScreen;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class MainMenuController{
    private final MainMenuScreen mainMenuScreen;
    private final Users users;
            
    public MainMenuController(MainMenuScreen mainMenuScreen, Users users){
        this.mainMenuScreen = mainMenuScreen;
        this.users = users;
    }
    
    public void navigateToLandsScreen(){
        long institutionId = this.users.getInstitution().getId();
        
        LandsScreen landsScreen = new LandsScreen(institutionId);
        landsScreen.setVisible(true);        
    }
}
