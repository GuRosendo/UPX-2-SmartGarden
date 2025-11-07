/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.view.model;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class LoginDto{
    private String users;
    private String userPassword;

    public LoginDto(String users, String userPassword){
        this.users = users;
        this.userPassword = userPassword;
    }

    public String getUsers(){
        return users;
    }

    public void setUsers(String users){
        this.users = users;
    }

    public String getUserPassword(){
        return userPassword;
    }

    public void setUserPassword(String userPassword){
        this.userPassword = userPassword;
    }
}
