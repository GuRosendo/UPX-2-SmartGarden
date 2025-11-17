/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.Addresses;
import com.facens.upx2.smartgarden.model.domain.Cities;
import com.facens.upx2.smartgarden.model.domain.VolunteerPlantings;
import com.facens.upx2.smartgarden.model.domain.Users;
import com.facens.upx2.smartgarden.model.domain.HeadInstitutionLands;
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
public class VolunteersPlantingsDao{
    private final DatabaseConnection databaseConnection;

    public VolunteersPlantingsDao(){
        this.databaseConnection = new MySQLDatabaseConnection();
    }
    
    public String save(VolunteerPlantings volunteerPlanting){
        return (volunteerPlanting.getId() == null || volunteerPlanting.getId() == 0L) ? add(volunteerPlanting) : edit(volunteerPlanting);
    }
    
    private String add(VolunteerPlantings volunteerPlanting){
        String queryAddVolunteer = "INSERT INTO volunteerPlantings(volunteer, headInstitutionLand, institution) VALUES (?, ?, ?)";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false); 

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryAddVolunteer, PreparedStatement.RETURN_GENERATED_KEYS)){
                preparedStatement.setLong(1, volunteerPlanting.getVolunteer().getId());
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
                preparedStatement.setLong(3, volunteerPlanting.getInstitution().getId());

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    
                    if(generatedKeys.next()){
                        volunteerPlanting.setId(generatedKeys.getLong(1));
                    }

                    connection.commit();
                    return "Voluntário adicionado com sucesso";
                }else{
                    connection.rollback();
                    return "Erro ao adicionar o voluntário";
                }

            }catch(SQLException e){
                connection.rollback();
                return "Erro SQL ao adicionar voluntário: " + e.getMessage();
            }
        }catch(SQLException e){
            return "Erro ao conectar ou finalizar transação: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    private String edit(VolunteerPlantings volunteerPlanting){
        String queryEditVolunteer = "UPDATE volunteerPlantings SET volunteer = ?, headInstitutionLand = ?, institution = ? WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false);

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryEditVolunteer)){
                preparedStatement.setLong(1, volunteerPlanting.getVolunteer().getId());
                preparedStatement.setNull(2, java.sql.Types.INTEGER);
                preparedStatement.setLong(3, volunteerPlanting.getInstitution().getId());
                preparedStatement.setLong(4, volunteerPlanting.getId());

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    connection.commit();
                    return "Voluntário editado com sucesso";
                }else{
                    connection.rollback();
                    return "Erro ao editar o voluntário";
                }

            }catch(SQLException e){
                connection.rollback();
                return "Erro SQL ao editar voluntário: " + e.getMessage();
            }
        }catch(SQLException e){
            return "Erro ao conectar ou finalizar transação: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    public String deleteVolunteerById(Long volunteerId){
        String query = "UPDATE volunteerPlantings SET deletedAt = NOW() WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setLong(1, volunteerId);

            int result = preparedStatement.executeUpdate();

            if(result == 1){
                return "Voluntário excluído com sucesso.";
            }else{
                return "Erro: voluntário não encontrado.";
            }

        }catch(SQLException e){
            return "Erro SQL ao excluir voluntário: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    private Users getVolunteer(ResultSet resultSet) throws SQLException{
        Users user = new Users();

        user.setId(resultSet.getLong("id"));

        long addressId = resultSet.getLong("userAddress");
        long institutionId = resultSet.getLong("institution");

        Addresses address = new AddressesDao().searchAddressById(addressId);
        Institutions institution = new InstitutionsDao().searchInstitutionById(institutionId);

        user.setUserAddress(address);
        user.setInstitution(institution);
        user.setFullName(resultSet.getString("fullName"));
        user.setUserEmail(resultSet.getString("userEmail"));
        user.setCreatedAt(resultSet.getObject("createdAt", LocalDateTime.class));
        user.setUpdatedAt(resultSet.getObject("updatedAt", LocalDateTime.class));
        user.setDeletedAt(resultSet.getObject("deletedAt", LocalDateTime.class));

        return user;
    }
    
     private Users getVolunteerWithoutClass(ResultSet resultSet) throws SQLException{
        Users user = new Users();
        Addresses address = new Addresses();
        Cities city = new Cities();
        
        user.setId(resultSet.getLong("id"));
        
        address.setId(resultSet.getLong("userAddress"));
        address.setCEP(resultSet.getString("cep"));
        address.setNeighborhoodName(resultSet.getString("neighborhoodName"));
        address.setStreetName(resultSet.getString("streetName"));
        address.setNumber(resultSet.getString("number"));
        
        city.setId(resultSet.getLong("cityId"));
        city.setName(resultSet.getString("cityName"));
        address.setCity(city);

        user.setUserAddress(address);
        user.setFullName(resultSet.getString("fullName"));
        user.setUserEmail(resultSet.getString("userEmail"));
        user.setCreatedAt(resultSet.getObject("createdAt", LocalDateTime.class));
        user.setUpdatedAt(resultSet.getObject("updatedAt", LocalDateTime.class));
        user.setDeletedAt(resultSet.getObject("deletedAt", LocalDateTime.class));

        return user;
    }
}
