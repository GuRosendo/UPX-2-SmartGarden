/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.model.dao.AuthenticationDao;
import com.facens.upx2.smartgarden.model.domain.Users;
import com.facens.upx2.smartgarden.controller.helper.DialogHelper;
import com.facens.upx2.smartgarden.view.form.LoginScreen;
import com.facens.upx2.smartgarden.view.form.MainMenuScreen;
import com.facens.upx2.smartgarden.view.model.LoginDto;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class LoginController{
    private final LoginScreen loginScreen;
    private final AuthenticationDao authenticationDao;

    public LoginController(LoginScreen loginScreen){
        this.loginScreen = loginScreen;
        this.authenticationDao = new AuthenticationDao();
    }
    
    public void login(){
        String user = this.loginScreen.getTxtLogin().getText();
        String password = this.loginScreen.getTxtPassword().getText();
        
        if(user.isEmpty()){
            DialogHelper.showMessage("O campo usuário deve ser preenchido", loginScreen);
            return;
        }
        
        if(password.isEmpty()){
            DialogHelper.showMessage("O campo senha deve ser preenchido", loginScreen);
            return;
        }
        
        if(password.length() < 8){
            DialogHelper.showMessage("Usuário ou senha incorreto(s)", loginScreen);
            return;
        }
        
        LoginDto loginDto = new LoginDto(user, password);
        
        Users users = this.authenticationDao.login(loginDto);
        
        if(users != null){ 
            MainMenuScreen mainMenuScreen = new MainMenuScreen(users);
            
            mainMenuScreen.setVisible(true);
            
            this.loginScreen.dispose();
            
            cleanInputs();
        }else{
            DialogHelper.showMessage("Usuário ou senha incorreto(s)", loginScreen);
        }
    }
    
    public void cancel(){
        boolean confirmation = DialogHelper.showConfirm("Deseja realmente sair?", "Sair", loginScreen);
        
        if(confirmation){
            System.exit(0);
        }
    }
    
    public void cleanInputs(){
        this.loginScreen.getTxtLogin().setText("");
        this.loginScreen.getTxtPassword().setText("");
    }
}
