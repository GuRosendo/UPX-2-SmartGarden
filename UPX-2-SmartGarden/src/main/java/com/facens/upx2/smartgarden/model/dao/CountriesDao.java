/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.Countries;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class CountriesDao{
    private final DatabaseConnection databaseConnection;

    public CountriesDao(){
        this.databaseConnection = new MySQLDatabaseConnection();
    }

    public List<Countries> searchCountry(){
        String querySearch = "SELECT * FROM countries";
        List<Countries> countryList = new ArrayList<>();

        try(PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(querySearch)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                countryList.add(getCountries(resultSet));
            }
        }catch(SQLException e){
            System.err.println("Erro ao listar países: " + e.getMessage());
        }

        return countryList;
    }

    public Countries searchCountryById(Long id){
        String querySearch = "SELECT * FROM countries WHERE id = ?";

        try(PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(querySearch)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getCountries(resultSet);
            }
        }catch(SQLException e){
            System.err.println("Erro ao buscar país por ID: " + e.getMessage());
        }

        return null;
    }

    private Countries getCountries(ResultSet resultSet) throws SQLException{
        Countries countries = new Countries();
        
        countries.setId(resultSet.getLong("id"));
        countries.setName(resultSet.getString("name"));
        countries.setNamePt(resultSet.getString("name_pt"));
        countries.setAcronym(resultSet.getString("acronym"));
        countries.setBacen(resultSet.getInt("bacen"));

        return countries;
    }
}
