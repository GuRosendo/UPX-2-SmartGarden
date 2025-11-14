/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.Countries;
import com.facens.upx2.smartgarden.model.domain.States;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author gusta
 */
public class StatesDao{
    private final DatabaseConnection databaseConnection;

    public StatesDao(){
        this.databaseConnection = new MySQLDatabaseConnection();
    }

    public List<States> searchStates(){
        String querySearch = "SELECT * FROM states";
        List<States> stateList = new ArrayList<>();

        try(PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(querySearch)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                stateList.add(getStates(resultSet));
            }
        }catch(SQLException e){
            System.err.println("Erro ao listar estados: " + e.getMessage());
        }finally {
            databaseConnection.closeConnection();
        }

        return stateList;
    }
    
    public List<States> searchStatesByCountryId(Long countryId){
        String querySearch = "SELECT * FROM states WHERE country = ? ORDER BY name ASC";
        List<States> stateList = new ArrayList<>();

        try(PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(querySearch)){
            preparedStatement.setLong(1, countryId);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                stateList.add(getStatesWithoutClass(resultSet));
            }
        }catch(SQLException e){
            System.err.println("Erro ao listar estados pelo ID do País: " + e.getMessage());
        }finally {
            databaseConnection.closeConnection();
        }

        return stateList;
    }

    public States searchStateById(Long id){
        String querySearch = "SELECT * FROM states WHERE id = ?";

        try(PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(querySearch)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getStates(resultSet);
            }
        }catch(SQLException e){
            System.err.println("Erro ao buscar estados por ID: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return null;
    }

    private States getStates(ResultSet resultSet) throws SQLException{
        States states = new States();
        
        long countryId = resultSet.getLong("country");
        
        Countries country = new CountriesDao().searchCountryById(countryId);
        
        states.setId(resultSet.getLong("id"));
        states.setName(resultSet.getString("name"));
        states.setUf(resultSet.getString("uf"));
        states.setIbge(resultSet.getInt("ibge"));
        states.setCountry(country);
        states.setDdd(resultSet.getString("ddd"));

        return states;
    }
    
    private States getStatesWithoutClass(ResultSet resultSet) throws SQLException {
        States states = new States();
        Countries country = new Countries();

        country.setId(resultSet.getLong("country"));

        states.setId(resultSet.getLong("id"));
        states.setName(resultSet.getString("name"));
        states.setUf(resultSet.getString("uf"));
        states.setIbge(resultSet.getInt("ibge"));
        states.setCountry(country);
        states.setDdd(resultSet.getString("ddd"));

        return states;
    }
}
