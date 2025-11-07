/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.model.dao.AuthenticationDao;
import com.facens.upx2.smartgarden.model.domain.Users;
import com.facens.upx2.smartgarden.view.form.LoginScreen;
import com.facens.upx2.smartgarden.view.model.LoginDto;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class LoginController implements ActionListener{
    private final LoginScreen loginScreen;
    private final AuthenticationDao authenticationDao;

    public LoginController(LoginScreen loginScreen){
        this.loginScreen = loginScreen;
        this.authenticationDao = new AuthenticationDao();
    }
    
    @Override
    public void actionPerformed(ActionEvent ae){
        String action = ae.getActionCommand().toLowerCase();
                
        switch(action){
            case "login": login(); break;
            case "cancel": cancel(); break;
        }
    }
    
    private void login(){
        String user = this.loginScreen.getTxtLogin().getText();
        String password = this.loginScreen.getTxtPassword().getText();
        
        if(user.equals("")){
            this.loginScreen.getLabelLoginMessage().setText("O campo usuário deve ser preenchido");
            return;
        }
        
        if(password.equals("")){
            this.loginScreen.getLabelLoginMessage().setText("O campo senha deve ser preenchido");
            return;
        }
        
        if(password.length() < 8){
            this.loginScreen.getLabelLoginMessage().setText("Usuário ou senha incorreto(s)");
            return;
        }
        
        LoginDto loginDto = new LoginDto(user, password);
        
        Users users = this.authenticationDao.login(loginDto);
        
        if(users != null){
            JOptionPane.showConfirmDialog(null, "Usuário logado com sucesso: " + users.getUserName());
            
            cleanInputs();
        }else{
            this.loginScreen.getLabelLoginMessage().setText("Usuário ou senha incorreto(s)");
        }
    }
    
    private void cancel(){
        int confirmation = JOptionPane.showConfirmDialog(loginScreen, "Deseja realmente sair?", "Sair", JOptionPane.YES_NO_OPTION);
        
        if(confirmation == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }
    
    public void cleanInputs(){
        this.loginScreen.getTxtLogin().setText("");
        this.loginScreen.getTxtPassword().setText("");
        this.loginScreen.getLabelLoginMessage().setText("");
    }
}
