/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.Addresses;
import com.facens.upx2.smartgarden.model.domain.Institutions;
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
public class InstitutionsDao{
    private final DatabaseConnection databaseConnection;

    public InstitutionsDao(){
        this.databaseConnection = new MySQLDatabaseConnection();
    }

    public String save(Institutions institution){
        return (institution.getId() == null || institution.getId() == 0L) ? add(institution) : edit(institution);
    }

    private String add(Institutions institution){
        Addresses address = institution.getInstitutionAddress();

        if(address == null){
            return "Erro: endereço não informado";
        }

        String resultAddress = new AddressesDao().save(address);

        if(!resultAddress.contains("sucesso")){
            return resultAddress;
        }

        String queryAddInstitution = "INSERT INTO institutions(institutionAddress, institutionName) VALUES (?, ?)";

        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(queryAddInstitution)){

            preparedStatement.setLong(1, address.getId());
            preparedStatement.setString(2, institution.getInstitutionName());

            int result = preparedStatement.executeUpdate();

            if(result == 1){
                return "Instituição adicionada com sucesso";
            }else{
                return "Erro ao adicionar instituição";
            }

        }catch(SQLException e){
            return "Erro SQL ao adicionar instituição: " + e.getMessage();
        }
    }

    private String edit(Institutions institution){
        Addresses address = institution.getInstitutionAddress();

        if(address == null){
            return "Erro: endereço não informado";
        }

        String resultAddress = new AddressesDao().save(address);

        if(!resultAddress.contains("sucesso")){
            return resultAddress;
        }

        String queryUpdateInstitution = "UPDATE institutions SET institutionAddress = ?, institutionName = ? WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateInstitution)){

            preparedStatement.setLong(1, address.getId());
            preparedStatement.setString(2, institution.getInstitutionName());
            preparedStatement.setLong(3, institution.getId());

            int result = preparedStatement.executeUpdate();

            if(result == 1){
                return "Instituição atualizada com sucesso";
            }else{
                return "Erro ao atualizar instituição";
            }

        }catch(SQLException e){
            return "Erro SQL ao atualizar instituição: " + e.getMessage();
        }
    }

    public List<Institutions> searchInstitution(){
        String querySearch = "SELECT * FROM institutions WHERE deletedAt IS NULL";
        List<Institutions> institutionList = new ArrayList<>();

        try(PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(querySearch)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                institutionList.add(getInstitution(resultSet));
            }
        }catch(SQLException e){
            System.err.println("Erro ao listar instituições: " + e.getMessage());
        }

        return institutionList;
    }

    public Institutions searchInstitutionById(Long id){
        String querySearch = "SELECT * FROM institutions WHERE id = ? AND deletedAt IS NULL";

        try(PreparedStatement preparedStatement = databaseConnection.getConnection().prepareStatement(querySearch)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getInstitution(resultSet);
            }
        }catch(SQLException e){
            System.err.println("Erro ao buscar instituição por ID: " + e.getMessage());
        }

        return null;
    }

    private Institutions getInstitution(ResultSet resultSet) throws SQLException{
        Institutions institution = new Institutions();

        institution.setId(resultSet.getLong("id"));

        long addressId = resultSet.getLong("institutionAddress");
        Addresses address = new AddressesDao().searchAddressById(addressId);

        institution.setInstitutionAddress(address);
        institution.setInstitutionName(resultSet.getString("institutionName"));
        institution.setCreatedAt(resultSet.getObject("createdAt", LocalDateTime.class));
        institution.setUpdatedAt(resultSet.getObject("updatedAt", LocalDateTime.class));
        institution.setDeletedAt(resultSet.getObject("deletedAt", LocalDateTime.class));

        return institution;
    }
}
