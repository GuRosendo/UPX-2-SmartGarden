/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.domain.Users;
import com.facens.upx2.smartgarden.model.exception.NegociateException;
import com.facens.upx2.smartgarden.view.model.LoginDto;
import javax.swing.JOptionPane;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class AuthenticationDao{
    private final UsersDao usersDao;
    
    public AuthenticationDao(){
        this.usersDao = new UsersDao();
    }
    
    public boolean hasPermission(Users users){
        try{
            permission(users);
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Usuário não possui permissão para realizar esta ação", 0);
            return false;
        }
    }
    
    private void permission(Users users){
        if(users.getInstitution() == null){
            throw new NegociateException("Usuário não possui permissão para realizar esta ação");
        }
    }
    
    public Users login(LoginDto login){
        Users users = usersDao.searchUserByEmail(login.getUsers());
        
        if(users == null){
            return null;
        }
        
        if(users.getUserPassword() != null && validatePassword(users.getUserPassword(), login.getUserPassword())){
            return users;
        }
        
        return null;
    }
    
    private boolean validatePassword(String userPassword, String loginPassword){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        
        return passwordEncoder.matches(loginPassword, userPassword);
    }
}
