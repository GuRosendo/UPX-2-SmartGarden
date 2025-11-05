/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class Main{
    public static void main(String[] args){
        DatabaseConnection databaseConnection = new MySQLDatabaseConnection();
                
        try{
            String query = "SELECT * FROM cropTypes;";

            ResultSet result = databaseConnection.getConnection().prepareStatement(query).executeQuery();
            
            while(result.next()){
                System.out.println(result.getString("name"));
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}