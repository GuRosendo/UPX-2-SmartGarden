/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.Cities;
import com.facens.upx2.smartgarden.model.domain.States;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class CitiesDao{
    private final DatabaseConnection databaseConnection;

    public CitiesDao(){
        this.databaseConnection = new MySQLDatabaseConnection();
    }

    public List<Cities> searchCity(){
        String querySearch = "SELECT * FROM cities";
        List<Cities> cityList = new ArrayList<>();

        try(PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(querySearch)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                cityList.add(getCities(resultSet));
            }
        }catch(SQLException e){
            System.err.println("Erro ao listar endereços: " + e.getMessage());
        }

        return cityList;
    }

    public Cities searchCityById(Long id){
        String querySearch = "SELECT * FROM cities WHERE id = ?";

        try(PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(querySearch)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getCities(resultSet);
            }
        }catch(SQLException e){
            System.err.println("Erro ao buscar endereço por ID: " + e.getMessage());
        }

        return null;
    }

    private Cities getCities(ResultSet resultSet) throws SQLException{
        Cities cities = new Cities();
        
        long stateId = resultSet.getLong("uf");
        
        States state = new StatesDao().searchStateById(stateId);
        
        cities.setId(resultSet.getLong("id"));
        cities.setName(resultSet.getString("name"));
        cities.setUf(state);
        cities.setIbge(resultSet.getInt("ibge"));

        return cities;
    }
}
