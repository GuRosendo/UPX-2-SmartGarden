/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.CropTypes;
import com.facens.upx2.smartgarden.model.domain.Institutions;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class CropTypesDao{
    private final DatabaseConnection databaseConnection;

    public CropTypesDao(){
        this.databaseConnection = new MySQLDatabaseConnection();
    }
    
    public String save(CropTypes cropType){
        return (cropType.getId() == null || cropType.getId() == 0L) ? add(cropType) : edit(cropType);
    }
    
    private String add(CropTypes cropType){
        String queryAddCropType = "INSERT INTO cropTypes(name, institution, seedingDateStart, seedingDateEnd) VALUES (?, ?, ?, ?)";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false); 

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryAddCropType, PreparedStatement.RETURN_GENERATED_KEYS)){
                preparedStatement.setString(1, cropType.getName());
                preparedStatement.setLong(2, cropType.getInstitution().getId());
                preparedStatement.setTimestamp(3, Timestamp.valueOf(cropType.getSeedingDateStart()));
                preparedStatement.setTimestamp(4, Timestamp.valueOf(cropType.getSeedingDateEnd()));

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    
                    if(generatedKeys.next()){
                        cropType.setId(generatedKeys.getLong(1));
                    }

                    connection.commit();
                    
                    return "Tipo de plantio adicionado com sucesso";
                }else{
                    connection.rollback();
                    return "Erro ao adicionar tipo de plantio";
                }

            }catch(SQLException e){
                connection.rollback();
                return "Erro SQL ao adicionar tipo de plantio: " + e.getMessage();
            }
        }catch(SQLException e){
            return "Erro ao conectar ou finalizar transação: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    private String edit(CropTypes cropType){
        String queryEditCropType = "UPDATE cropTypes SET name = ?, seedingDateStart = ?, seedingDateEnd = ? WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false);

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryEditCropType)){
                preparedStatement.setString(1, cropType.getName());
                preparedStatement.setTimestamp(2, Timestamp.valueOf(cropType.getSeedingDateStart()));
                preparedStatement.setTimestamp(3, Timestamp.valueOf(cropType.getSeedingDateEnd()));
                preparedStatement.setLong(4, cropType.getId());

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    connection.commit();
                    
                    return "Tipo de plantio editado com sucesso";
                }else{
                    connection.rollback();
                    return "Erro ao editar tipo de plantio";
                }

            }catch(SQLException e){
                connection.rollback();
                return "Erro SQL ao editar tipo de plantio: " + e.getMessage();
            }
        }catch(SQLException e){
            return "Erro ao conectar ou finalizar transação: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    public List<CropTypes> getCropTypes(Long institutionId){
        String querySearch = "SELECT * FROM cropTypes WHERE institution = ? AND deletedAt IS NULL ORDER BY createdAt DESC;";
        List<CropTypes> cropTypesList = new ArrayList<>();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setLong(1, institutionId);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                cropTypesList.add(getCropTypesWithoutClass(resultSet));
            }

        }catch(SQLException e){
            System.err.println("Erro ao listar tipo de plantio: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return cropTypesList;
    }
    
    public List<CropTypes> getCropTypesWithSearch(Long institutionId, String search){
        String querySearch = "SELECT * FROM cropTypes WHERE name LIKE ? AND institution = ? AND deletedAt IS NULL ORDER BY createdAt DESC;";
        List<CropTypes> cropTypesList = new ArrayList<>();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setString(1, search + "%");
            preparedStatement.setLong(2, institutionId);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                cropTypesList.add(getCropTypesWithoutClass(resultSet));
            }

        }catch(SQLException e){
            System.err.println("Erro ao listar tipo de plantio: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return cropTypesList;
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
    
    private CropTypes getCropTypes(ResultSet resultSet) throws SQLException{
        CropTypes cropTypes = new CropTypes();

        cropTypes.setId(resultSet.getLong("id"));

        long institutionId = resultSet.getLong("institution");

        Institutions institution = new InstitutionsDao().searchInstitutionById(institutionId);

        cropTypes.setId(resultSet.getLong("id"));
        cropTypes.setInstitution(institution);
        cropTypes.setName(resultSet.getString("name"));
        cropTypes.setSeedingDateStart(resultSet.getTimestamp("seedingDateStart").toLocalDateTime());
        cropTypes.setSeedingDateEnd(resultSet.getTimestamp("seedingDateEnd").toLocalDateTime());
        cropTypes.setCreatedAt(resultSet.getObject("createdAt", LocalDateTime.class));
        cropTypes.setUpdatedAt(resultSet.getObject("updatedAt", LocalDateTime.class));
        cropTypes.setDeletedAt(resultSet.getObject("deletedAt", LocalDateTime.class));

        return cropTypes;
    }
    
    private CropTypes getCropTypesWithoutClass(ResultSet resultSet) throws SQLException{
        CropTypes cropTypes = new CropTypes();
        Institutions institutions = new Institutions();
        
        cropTypes.setId(resultSet.getLong("id"));
        cropTypes.setName(resultSet.getString("name"));
        cropTypes.setInstitution(institutions);
        cropTypes.setSeedingDateStart(resultSet.getTimestamp("seedingDateStart").toLocalDateTime());
        cropTypes.setSeedingDateEnd(resultSet.getTimestamp("seedingDateEnd").toLocalDateTime());
        cropTypes.setCreatedAt(resultSet.getObject("createdAt", LocalDateTime.class));
        cropTypes.setUpdatedAt(resultSet.getObject("updatedAt", LocalDateTime.class));
        cropTypes.setDeletedAt(resultSet.getObject("deletedAt", LocalDateTime.class));

        return cropTypes;
    }
}
