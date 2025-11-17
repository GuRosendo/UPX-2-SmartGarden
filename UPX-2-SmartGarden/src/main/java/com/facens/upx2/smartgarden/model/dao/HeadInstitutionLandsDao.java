/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.HeadInstitutionLands;
import com.facens.upx2.smartgarden.model.domain.Institutions;
import com.facens.upx2.smartgarden.model.domain.Lands;
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
public class HeadInstitutionLandsDao{
    private final DatabaseConnection databaseConnection;

    public HeadInstitutionLandsDao(){
        this.databaseConnection = new MySQLDatabaseConnection();
    }
    
    public String save(HeadInstitutionLands headInstitutionLand){
        return (headInstitutionLand.getId() == null || headInstitutionLand.getId() == 0L) ? add(headInstitutionLand) : edit(headInstitutionLand);
    }
    
    private String add(HeadInstitutionLands headInstitutionLand){
        Institutions institution = headInstitutionLand.getInstitution();
        Lands land = headInstitutionLand.getLand();

        if(institution == null){
            return "Erro: instituição não informada";
        }
        
        if(land == null){
            return "Erro: terreno não informado";
        }
        
        String queryAddHeadInstitution = "INSERT INTO headInstitutionLands(institution, land) VALUES (?, ?)";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false); // inicia transação

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryAddHeadInstitution)){
                preparedStatement.setLong(1, institution.getId());
                preparedStatement.setLong(2, land.getId());

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    connection.commit();
                    
                    return "Instituição chefe adicionada com sucesso";
                }else{
                    connection.rollback();
                    return "Erro ao adicionar instituição chefe";
                }

            }catch(SQLException e){
                connection.rollback();
                return "Erro SQL ao adicionar instituição chefe: " + e.getMessage();
            }
        }catch(SQLException e){
            return "Erro ao conectar ou finalizar transação: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    private String edit(HeadInstitutionLands headInstitutionLand){
        Institutions institution = headInstitutionLand.getInstitution();
        Lands land = headInstitutionLand.getLand();

        if(institution == null){
            return "Erro: instituição não informada";
        }
        
        if(land == null){
            return "Erro: terreno não informado";
        }

        String queryEditHeadInstitution = "UPDATE headInstitutionLands SET institution = ?, land = ? WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false); // inicia transação

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryEditHeadInstitution)){
                preparedStatement.setLong(1, institution.getId());
                preparedStatement.setLong(2, land.getId());
                preparedStatement.setLong(3, headInstitutionLand.getId());

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    connection.commit();
                    
                    return "Instituição chefe editada com sucesso (ID: " + headInstitutionLand.getId() + ")";
                }else{
                    connection.rollback();
                    return "Erro ao editar instituição chefe";
                }

            }catch(SQLException e){
                connection.rollback();
                return "Erro SQL ao editar instituição chefe: " + e.getMessage();
            }
        }catch(SQLException e){
            return "Erro ao conectar ou finalizar transação: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    public HeadInstitutionLands searchHeadInstitutionLandById(Long institutionId){
        String querySearch = "SELECT * FROM headInstitutionLands WHERE id = ? AND deletedAt IS NULL;";
        List<HeadInstitutionLands> headInstitutionLandsList = new ArrayList<>();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setLong(1, institutionId);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                headInstitutionLandsList.add(getHeadInstitutionLand(resultSet));
            }

        }catch(SQLException e){
            System.err.println("Erro ao listar tipo de plantio por ID: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return null;
    }
    
    public HeadInstitutionLands getHeadInstitutionLandByInstitutionAndLand(Long institutionId, Long landId){
        String querySearch = "SELECT * FROM headInstitutionLands WHERE institution = ? AND land = ? AND deletedAt IS NULL;";
        HeadInstitutionLands result = null;

        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){

            preparedStatement.setLong(1, institutionId);
            preparedStatement.setLong(2, landId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if(resultSet.next()){
                result = getHeadInstitutionLand(resultSet);
            }

        }catch(SQLException e){
            System.err.println("Erro ao listar tipo de plantio por ID da instuição e terreno: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return result;
    }
    
    private HeadInstitutionLands getHeadInstitutionLand(ResultSet resultSet) throws SQLException {
        HeadInstitutionLands head = new HeadInstitutionLands();

        head.setId(resultSet.getLong("id"));

        long institutionId = resultSet.getLong("institution");
        long landId = resultSet.getLong("land");

        Institutions institution = new InstitutionsDao().searchInstitutionById(institutionId);
        Lands land = new LandsDao().searchLandById(landId);

        head.setInstitution(institution);
        head.setLand(land);

        head.setCreatedAt(resultSet.getObject("createdAt", LocalDateTime.class));
        head.setUpdatedAt(resultSet.getObject("updatedAt", LocalDateTime.class));
        head.setDeletedAt(resultSet.getObject("deletedAt", LocalDateTime.class));

        return head;
    }
}