/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.Addresses;
import com.facens.upx2.smartgarden.model.domain.Cities;
import com.facens.upx2.smartgarden.model.domain.Institutions;
import com.facens.upx2.smartgarden.model.domain.Users;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */

public class VolunteersDao{
    private final DatabaseConnection databaseConnection;

    public VolunteersDao(){
        this.databaseConnection = new MySQLDatabaseConnection();
    }

    public String save(Users user){
        return (user.getId() == null || user.getId() == 0L) ? add(user) : edit(user);
    }

    private String add(Users user){
        Addresses address = user.getUserAddress();

        if(address == null){
            return "Erro: endereço não informado";
        }

        String resultAddress = new AddressesDao().save(address);

        if(!resultAddress.contains("sucesso")){
            return resultAddress;
        }

        String queryAddUser = "INSERT INTO users(userAddress, institution, fullName, userEmail) VALUES (?, ?, ?, ?)";

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(queryAddUser)){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                        
            preparedStatement.setLong(1, address.getId());
            preparedStatement.setLong(2, user.getInstitution().getId());
            preparedStatement.setString(3, user.getFullName());
            preparedStatement.setString(4, user.getUserEmail());

            int result = preparedStatement.executeUpdate();

            if(result == 1){
                return "Usuário adicionado com sucesso";
            }else{
                return "Erro ao adicionar o usuário";
            }

        }catch(SQLException e){
            return "Erro SQL ao adicionar usuário: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }

    private String edit(Users user){
        Addresses address = user.getUserAddress();

        if(address == null){
            return "Erro: endereço não informado";
        }

        String resultAddress = new AddressesDao().save(address);

        if(!resultAddress.contains("sucesso")){
            return resultAddress;
        }

        String queryUpdateUser = "UPDATE users SET userAddress = ?, institution = ?, fullName = ?, userEmail = ? WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateUser)){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            
            String passwordEncryptred = passwordEncoder.encode(user.getUserPassword());
            
            preparedStatement.setLong(1, address.getId());
            preparedStatement.setLong(2, user.getInstitution().getId());
            preparedStatement.setString(3, user.getFullName());
            preparedStatement.setString(4, user.getUserEmail());
            preparedStatement.setLong(5, user.getId());

            int result = preparedStatement.executeUpdate();

            if(result == 1){
                return "Usuário editado com sucesso";
            }else{
                return "Erro ao editar o usuário";
            }

        }catch(SQLException e){
            return "Erro SQL ao editar usuário: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }

    public List<Users> searchAllVolunteers(Long institutionId){
       String querySearch = "SELECT " +
        "    user.id AS id, " +
        "    user.fullName AS fullName, " +
        "    user.userName AS userName, " +
        "    user.userEmail AS userEmail, " +
        "    user.createdAt AS createdAt, " +
        "    user.updatedAt AS updatedAt, " +
        "    user.deletedAt AS deletedAt, " +
        "    addr.id AS userAddress, " +
        "    addr.cep AS cep, " +
        "    addr.neighborhoodName AS neighborhoodName, " +
        "    addr.streetName AS streetName, " +
        "    addr.number AS number, " +
        "    city.id AS cityId, " +
        "    city.name AS cityName " +
        "FROM users user " +
        "INNER JOIN addresses addr ON user.userAddress = addr.id " +
        "INNER JOIN cities city ON addr.city = city.id " +
        "INNER JOIN institutions inst ON user.institution = inst.id " +
        "WHERE user.institution = ? " +
        "  AND user.deletedAt IS NULL " +
        "  AND user.userName IS NULL " +
        "  AND user.userPassword IS NULL " +
        "  AND addr.deletedAt IS NULL " +
        "  AND inst.deletedAt IS NULL " +
        "  AND addr.type = 1 " +
        "ORDER BY user.fullName ASC";
       
        List<Users> usersList = new ArrayList<>();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setLong(1, institutionId);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                usersList.add(getUserWithoutClass(resultSet));
            }

        }catch(SQLException e){
            System.err.println("Erro ao listar usuário: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return usersList;
   }
    
    public List<Users> searchAllVolunteersWithSearch(Long institutionId, String search){
        String querySearch = "SELECT " +
        "    user.id AS id, " +
        "    user.fullName AS fullName, " +
        "    user.userName AS userName, " +
        "    user.userEmail AS userEmail, " +
        "    user.createdAt AS createdAt, " +
        "    user.updatedAt AS updatedAt, " +
        "    user.deletedAt AS deletedAt, " +
        "    addr.id AS userAddress, " +
        "    addr.cep AS cep, " +
        "    addr.neighborhoodName AS neighborhoodName, " +
        "    addr.streetName AS streetName, " +
        "    addr.number AS number, " +
        "    city.id AS cityId, " +
        "    city.name AS cityName " +
        "FROM users user " +
        "INNER JOIN addresses addr ON user.userAddress = addr.id " +
        "INNER JOIN cities city ON addr.city = city.id " +
        "INNER JOIN institutions inst ON user.institution = inst.id " +
        "WHERE user.institution = ? " +
        "  AND user.fullName LIKE ? " +
        "  AND user.userName IS NULL " +
        "  AND user.userPassword IS NULL " +
        "  AND user.deletedAt IS NULL " +
        "  AND addr.deletedAt IS NULL " +
        "  AND inst.deletedAt IS NULL " +
        "  AND addr.type = 1 " +
        "ORDER BY user.fullName ASC";
        
        List<Users> usersList = new ArrayList<>();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setLong(1, institutionId);
            preparedStatement.setString(2, search + "%");
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                usersList.add(getUserWithoutClass(resultSet));
            }

        }catch(SQLException e){
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return usersList;
    }

    public Users searchVolunteerByEmail(String email){
        String querySearch = "SELECT * FROM users usr INNER JOIN institutions inst ON usr.institution = inst.id WHERE usr.userEmail = ? AND usr.deletedAt IS NULL AND usr.userPassword IS NULL";

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getUser(resultSet);
            }
        }catch(SQLException e){
            System.err.println("Erro ao listar usuário por email: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return null;
    }
    
    public String deleteVolunteerById(Long userId){
        String query = "UPDATE users SET deletedAt = NOW() WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setLong(1, userId);

            int result = preparedStatement.executeUpdate();

            if(result == 1){
                return "Usuário excluído com sucesso.";
            }else{
                return "Erro: usuário não encontrado.";
            }

        }catch(SQLException e){
            return "Erro SQL ao excluir usuário: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }

    private Users getUser(ResultSet resultSet) throws SQLException{
        Users user = new Users();

        user.setId(resultSet.getLong("id"));

        long addressId = resultSet.getLong("userAddress");
        long institutionId = resultSet.getLong("institution");

        Addresses address = new AddressesDao().searchAddressById(addressId);
        Institutions institution = new InstitutionsDao().searchInstitutionById(institutionId);

        user.setUserAddress(address);
        user.setInstitution(institution);
        user.setFullName(resultSet.getString("fullName"));
        user.setUserName(resultSet.getString("userName"));
        user.setUserEmail(resultSet.getString("userEmail"));
        user.setUserPassword(resultSet.getString("userPassword"));
        user.setCreatedAt(resultSet.getObject("createdAt", LocalDateTime.class));
        user.setUpdatedAt(resultSet.getObject("updatedAt", LocalDateTime.class));
        user.setDeletedAt(resultSet.getObject("deletedAt", LocalDateTime.class));

        return user;
    }
    
     private Users getUserWithoutClass(ResultSet resultSet) throws SQLException{
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
        user.setUserName(resultSet.getString("userName"));
        user.setUserEmail(resultSet.getString("userEmail"));
        user.setCreatedAt(resultSet.getObject("createdAt", LocalDateTime.class));
        user.setUpdatedAt(resultSet.getObject("updatedAt", LocalDateTime.class));
        user.setDeletedAt(resultSet.getObject("deletedAt", LocalDateTime.class));

        return user;
    }
}
