/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.facens.upx2.smartgarden.model.database.connection;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public interface DatabaseConnection{
    public Connection getConnection() throws SQLException;
    
    void closeConnection();
}
