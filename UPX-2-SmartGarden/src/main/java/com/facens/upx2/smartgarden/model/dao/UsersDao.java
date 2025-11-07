/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.Addresses;
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

public class UsersDao{
    private final DatabaseConnection databaseConnection;

    public UsersDao(){
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

        Users userAlreadyExists = searchUserByEmail(user.getUserEmail());

        if(userAlreadyExists != null){
            return String.format("Erro: usuário %s já existente", user.getUserEmail());
        }

        String resultAddress = new AddressesDao().save(address);

        if(!resultAddress.contains("sucesso")){
            return resultAddress;
        }

        String queryAddUser = "INSERT INTO users(userAddress, institution, fullName, userName, userEmail, userPassword) VALUES (?, ?, ?, ?, ?, ?)";

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(queryAddUser)){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            
            String passwordEncryptred = passwordEncoder.encode(user.getUserPassword());
            
            preparedStatement.setLong(1, address.getId());
            preparedStatement.setLong(2, user.getInstitution().getId());
            preparedStatement.setString(3, user.getFullName());
            preparedStatement.setString(4, user.getUserName());
            preparedStatement.setString(5, user.getUserEmail());
            preparedStatement.setString(6, passwordEncryptred);

            int result = preparedStatement.executeUpdate();

            if(result == 1){
                return "Usuário adicionado com sucesso";
            }else{
                return "Erro ao adicionar o usuário";
            }

        }catch(SQLException e){
            return "Erro SQL ao adicionar usuário: " + e.getMessage();
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

        String queryUpdateUser = "UPDATE users SET userAddress = ?, institution = ?, fullName = ?, userName = ?, userEmail = ?, userPassword = ? WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(queryUpdateUser)){
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            
            String passwordEncryptred = passwordEncoder.encode(user.getUserPassword());
            
            preparedStatement.setLong(1, address.getId());
            preparedStatement.setLong(2, user.getInstitution().getId());
            preparedStatement.setString(3, user.getFullName());
            preparedStatement.setString(4, user.getUserName());
            preparedStatement.setString(5, user.getUserEmail());
            preparedStatement.setString(6, passwordEncryptred);
            preparedStatement.setLong(7, user.getId());

            int result = preparedStatement.executeUpdate();

            if(result == 1){
                return "Usuário editado com sucesso";
            }else{
                return "Erro ao editar o usuário";
            }

        }catch(SQLException e){
            return "Erro SQL ao editar usuário: " + e.getMessage();
        }
    }

    public List<Users> searchAllUsers() {
        String querySearch = "SELECT * FROM users WHERE deletedAt IS NULL";
        List<Users> usersList = new ArrayList<>();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                usersList.add(getUser(resultSet));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários: " + e.getMessage());
        }

        return usersList;
    }

    public Users searchUserById(Long id){
        String querySearch = "SELECT * FROM users WHERE id = ? AND deletedAt IS NULL";

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getUser(resultSet);
            }
        }catch(SQLException e){
            System.err.println("Erro ao listar usuário por id: " + e.getMessage());
        }

        return null;
    }

    public Users searchUserByEmail(String email){
        String querySearch = "SELECT * FROM users WHERE userEmail = ? AND deletedAt IS NULL";

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                return getUser(resultSet);
            }
        }catch(SQLException e){
            System.err.println("Erro ao listar usuário por email: " + e.getMessage());
        }

        return null;
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
}
