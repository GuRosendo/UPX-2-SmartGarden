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
        return add(institution);
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

        String queryAddInstitution = "INSERT INTO institutions(institutionAddress, institutionName, institutionCNPJ) VALUES (?, ?, ?)";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false); 

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryAddInstitution, PreparedStatement.RETURN_GENERATED_KEYS)){
                preparedStatement.setLong(1, address.getId());
                preparedStatement.setString(2, institution.getInstitutionName());
                preparedStatement.setString(3, institution.getInstitutionCNPJ());

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    ResultSet keys = preparedStatement.getGeneratedKeys();
                    
                    if(keys.next()){
                        institution.setId(keys.getLong(1));
                    }

                    connection.commit();
                    
                    return "Instituição adicionada com sucesso";
                }else{
                    connection.rollback();
                    
                    return "Erro ao adicionar instituição";
                }

            }catch(SQLException e){
                connection.rollback();
                
                return "Erro SQL ao adicionar instituição: " + e.getMessage();
            }

        }catch(SQLException e){
            return "Erro ao conectar ou finalizar transação: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }

    public List<Institutions> searchInstitution() {
        String query = "SELECT * FROM institutions WHERE deletedAt IS NULL";
        List<Institutions> list = new ArrayList<>();

        try(PreparedStatement ps = databaseConnection.getConnection().prepareStatement(query)){
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                list.add(getInstitution(rs));
            }

        }catch(SQLException e){
            System.err.println("Erro ao listar instituições: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return list;
    }

    public Institutions searchInstitutionById(Long id){
        String query = "SELECT * FROM institutions WHERE id = ? AND deletedAt IS NULL";

        try(PreparedStatement ps = databaseConnection.getConnection().prepareStatement(query)){
            ps.setLong(1, id);

            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                return getInstitution(rs);
            }

        }catch(SQLException e){
            System.err.println("Erro ao buscar instituição por ID: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return null;
    }

    private Institutions getInstitution(ResultSet rs) throws SQLException{
        Institutions institution = new Institutions();

        institution.setId(rs.getLong("id"));

        long addressId = rs.getLong("institutionAddress");
        Addresses address = new AddressesDao().searchAddressById(addressId);

        institution.setInstitutionAddress(address);
        institution.setInstitutionName(rs.getString("institutionName"));
        institution.setInstitutionCNPJ(rs.getString("institutionCNPJ"));
        institution.setCreatedAt(rs.getObject("createdAt", LocalDateTime.class));
        institution.setUpdatedAt(rs.getObject("updatedAt", LocalDateTime.class));
        institution.setDeletedAt(rs.getObject("deletedAt", LocalDateTime.class));

        return institution;
    }
}
