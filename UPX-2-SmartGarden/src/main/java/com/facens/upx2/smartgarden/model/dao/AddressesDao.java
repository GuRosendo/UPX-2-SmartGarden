/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.Addresses;
import com.facens.upx2.smartgarden.model.domain.Cities;
import com.facens.upx2.smartgarden.model.domain.Countries;
import com.facens.upx2.smartgarden.model.domain.States;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */

public class AddressesDao{
    private final DatabaseConnection databaseConnection;

    public AddressesDao(){
        this.databaseConnection = new MySQLDatabaseConnection();
    }

    public String save(Addresses address){
        return (address.getId() == null || address.getId() == 0L) ? add(address) : edit(address);
    }

    private String add(Addresses address){
        String queryAdd = "INSERT INTO addresses(CEP, country, state, city, neighborhoodName, streetName, number, type) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false);

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryAdd)){
                insertAddressValues(preparedStatement, address);

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    connection.commit();
                    return "Endereço adicionado com sucesso";
                }else{
                    connection.rollback();
                    return "Erro ao adicionar endereço";
                }
            }

        }catch(SQLException e){
            return "Erro SQL ao adicionar endereço: " + e.getMessage();
        }
    }

    private String edit(Addresses address){
        String queryUpdate = "UPDATE addresses SET CEP = ?, country = ?, state = ?, city = ?, neighborhoodName = ?, streetName = ?, number = ?, type = ? WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false);

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryUpdate)){
                insertAddressValues(preparedStatement, address);
                preparedStatement.setLong(9, address.getId());

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    connection.commit();
                    return "Endereço atualizado com sucesso";
                }else{
                    connection.rollback();
                    return "Erro ao atualizar endereço";
                }
            }

        }catch(SQLException e){
            return "Erro SQL ao atualizar endereço: " + e.getMessage();
        }
    }

    public List<Addresses> searchAddress(){
        String querySearch = "SELECT * FROM addresses WHERE deletedAt IS NULL";
        List<Addresses> addressList = new ArrayList<>();

        try(PreparedStatement stmt = databaseConnection.getConnection().prepareStatement(querySearch)){
            ResultSet resultSet = stmt.executeQuery();

            while(resultSet.next()){
                addressList.add(getAddress(resultSet));
            }
        }catch(SQLException e){
            System.err.println("Erro ao listar endereços: " + e.getMessage());
        }

        return addressList;
    }

    public Addresses searchAddressById(Long id){
        String querySearch = "SELECT * FROM addresses WHERE id = ? AND deletedAt IS NULL";

        try(PreparedStatement stmt = databaseConnection.getConnection().prepareStatement(querySearch)){
            stmt.setLong(1, id);

            ResultSet resultSet = stmt.executeQuery();

            if(resultSet.next()){
                return getAddress(resultSet);
            }
        }catch(SQLException e){
            System.err.println("Erro ao buscar endereço por ID: " + e.getMessage());
        }

        return null;
    }

    private Addresses getAddress(ResultSet resultSet) throws SQLException{
        Addresses address = new Addresses();
        
        long countryId = resultSet.getLong("country");
        long stateId = resultSet.getLong("state");
        long cityId = resultSet.getLong("city");

        Countries country = new CountriesDao().searchCountryById(countryId);
        
        States state = new StatesDao().searchStateById(stateId);
        
        Cities city = new CitiesDao().searchCityById(cityId);

        address.setId(resultSet.getLong("id"));
        address.setCEP(resultSet.getString("CEP"));
        address.setCountry(country);
        address.setState(state);
        address.setCity(city);
        address.setNeighborhoodName(resultSet.getString("neighborhoodName"));
        address.setStreetName(resultSet.getString("streetName"));
        address.setNumber(resultSet.getString("number"));
        address.setType(resultSet.getInt("type"));
        address.setCreatedAt(resultSet.getObject("createdAt", LocalDateTime.class));
        address.setUpdatedAt(resultSet.getObject("updatedAt", LocalDateTime.class));
        address.setDeletedAt(resultSet.getObject("deletedAt", LocalDateTime.class));

        return address;
    }

    private void insertAddressValues(PreparedStatement preparedStatement, Addresses address) throws SQLException{
        preparedStatement.setString(1, address.getCEP());
        preparedStatement.setLong(2, address.getCountry().getId());
        preparedStatement.setLong(3, address.getState().getId());
        preparedStatement.setLong(4, address.getCity().getId());
        preparedStatement.setString(5, address.getNeighborhoodName());
        preparedStatement.setString(6, address.getStreetName());
        preparedStatement.setString(7, address.getNumber());
        preparedStatement.setInt(8, address.getType());
    }
}
