/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.Plantings;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class PlantingsDao{
    private final DatabaseConnection databaseConnection;

    public PlantingsDao(){
        this.databaseConnection = new MySQLDatabaseConnection();
    }
    
    public String save(Plantings planting){
        return (planting.getId() == null || planting.getId() == 0L) ? add(planting) : edit(planting);
    }
    
    private String add(Plantings planting){
        String queryAddPlanting = "INSERT INTO plantings(headInstitutionLand, cropType, approximatedHarvestDate) VALUES (?, ?, ?)";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false); 

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryAddPlanting, PreparedStatement.RETURN_GENERATED_KEYS)){
                preparedStatement.setLong(1, planting.getHeadInstitutionLand().getId());
                preparedStatement.setLong(2, planting.getCropType().getId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(planting.getApproximateHarvestDate()));

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    
                    if(generatedKeys.next()){
                        planting.setId(generatedKeys.getLong(1));
                    }

                    connection.commit();
                    
                    return "Horta adicionada com sucesso";
                }else{
                    connection.rollback();
                    return "Erro ao adicionar horta";
                }

            }catch(SQLException e){
                connection.rollback();
                return "Erro SQL ao adicionar horta: " + e.getMessage();
            }
        }catch(SQLException e){
            return "Erro ao conectar ou finalizar transação: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    private String edit(Plantings planting){
        String queryEditCropType = "UPDATE planting SET approximatedHarvestDate = ? WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false);

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryEditCropType)){
                preparedStatement.setTimestamp(1, Timestamp.valueOf(planting.getApproximateHarvestDate()));
                preparedStatement.setLong(2, planting.getId());

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    connection.commit();
                    
                    return "Horta editada com sucesso";
                }else{
                    connection.rollback();
                    return "Erro ao editar horta";
                }

            }catch(SQLException e){
                connection.rollback();
                return "Erro SQL ao editar horta: " + e.getMessage();
            }
        }catch(SQLException e){
            return "Erro ao conectar ou finalizar transação: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    public List<Plantings> getPlantings(Long institutionId){
        String querySearch = "SELECT * FROM cropTypes WHERE institution = ? AND deletedAt IS NULL ORDER BY createdAt DESC;";
        List<Plantings> plantingsList = new ArrayList<>();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setLong(1, institutionId);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                plantingsList.add(getPlantingsWithoutClass(resultSet));
            }

        }catch(SQLException e){
            System.err.println("Erro ao listar tipo de plantio: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return plantingsList;
    }
    
    public List<Plantings> getCropTypesWithSearch(Long institutionId, String search){
        String querySearch = "SELECT * FROM cropTypes WHERE name LIKE ? AND institution = ? AND deletedAt IS NULL ORDER BY createdAt DESC;";
        List<Plantings> plantingsList = new ArrayList<>();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setString(1, search + "%");
            preparedStatement.setLong(2, institutionId);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                plantingsList.add(getPlantingsWithoutClass(resultSet));
            }

        }catch(SQLException e){
            System.err.println("Erro ao listar tipo de plantio: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return plantingsList;
    }
    
    public String deleteCropTypeById(Long cropTypeId){
        String query = "UPDATE cropTypes SET deletedAt = NOW() WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setLong(1, cropTypeId);

            int result = preparedStatement.executeUpdate();

            if(result == 1){
                return "Tipo de plantio excluído com sucesso.";
            }else{
                return "Erro: tipo de plantio não encontrado.";
            }

        }catch(SQLException e){
            return "Erro SQL ao excluir tipo plantio: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    private Plantings getPlantings(ResultSet resultSet) throws SQLException{
        Plantings plantings = new Plantings();
        
        return null;
    }
    
    private Plantings getPlantingsWithoutClass(ResultSet resultSet) throws SQLException{
        Plantings plantings = new Plantings();
        
        return null;
    }
}
