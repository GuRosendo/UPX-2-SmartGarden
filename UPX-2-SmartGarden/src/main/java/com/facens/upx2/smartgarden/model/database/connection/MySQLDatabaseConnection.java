/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.database.connection;

import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class MySQLDatabaseConnection implements DatabaseConnection {
    private final Dotenv dotenv = Dotenv.load();

    private final String URL = dotenv.get("DB_URL");
    private final String USER = dotenv.get("DB_USER");
    private final String PASSWORD = dotenv.get("DB_PASSWORD");

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}

