/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.CropTypes;
import com.facens.upx2.smartgarden.model.domain.HeadInstitutionLands;
import com.facens.upx2.smartgarden.model.domain.Lands;
import com.facens.upx2.smartgarden.model.domain.Plantings;
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
        String querySearch = "SELECT pl.id AS id, la.landName AS landName, COUNT(vlp.id) AS totalVolunteers, SUM(plc.cost) AS plantingCosts, SUM(pp.profit) AS plantingProfit, SUM(pp.weight) AS plantingWeight, pl.approximatedHarvestDate AS approximatedHarvestDate, pl.createdAt AS createdAt FROM plantings pl INNER JOIN headInstitutionLands hdi ON pl.headInstitutionLand = hdi.id LEFT JOIN lands la ON hdi.land = la.id LEFT JOIN institutions inst ON hdi.institution = inst.id LEFT JOIN volunteerPlantings vlp ON hdi.institution = vlp.headInstitutionLand LEFT JOIN users usr ON vlp.volunteer = usr.id LEFT JOIN plantingCosts plc ON hdi.institution = plc.headInstitutionLand LEFT JOIN plantingsProductions pp ON hdi.institution = pp.headInstitutionLand WHERE hdi.institution = ? AND pl.deletedAt IS NULL AND hdi.deletedAt IS NULL AND la.deletedAt IS NULL AND inst.deletedAt IS NULL AND vlp.deletedAt IS NULL AND usr.deletedAt IS NULL AND plc.deletedAt IS NULL AND pp.deletedAt IS NULL GROUP BY pl.id;";
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
    
    public List<Plantings> getPlantingsWithSearch(Long institutionId, String search){
        String querySearch = "SELECT pl.id AS id, la.landName AS landName, COUNT(vlp.id) AS totalVolunteers, SUM(plc.cost) AS plantingCosts, SUM(pp.profit) AS plantingProfit, SUM(pp.weight) AS plantingWeight, pl.approximatedHarvestDate AS approximatedHarvestDate, pl.createdAt AS createdAt FROM plantings pl INNER JOIN headInstitutionLands hdi ON pl.headInstitutionLand = hdi.id LEFT JOIN lands la ON hdi.land = la.id LEFT JOIN institutions inst ON hdi.institution = inst.id LEFT JOIN volunteerPlantings vlp ON hdi.institution = vlp.headInstitutionLand LEFT JOIN users usr ON vlp.volunteer = usr.id LEFT JOIN plantingCosts plc ON hdi.institution = plc.headInstitutionLand LEFT JOIN plantingsProductions pp ON hdi.institution = pp.headInstitutionLand WHERE hdi.institution = ? AND la.landName LIKE ? AND pl.deletedAt IS NULL AND hdi.deletedAt IS NULL AND la.deletedAt IS NULL AND inst.deletedAt IS NULL AND vlp.deletedAt IS NULL AND usr.deletedAt IS NULL AND plc.deletedAt IS NULL AND pp.deletedAt IS NULL GROUP BY pl.id;";
        List<Plantings> plantingsList = new ArrayList<>();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setLong(1, institutionId);
            preparedStatement.setString(2, search + "%");
            
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
    
    private Plantings getPlantings(ResultSet resultSet) throws SQLException {
        Plantings plantings = new Plantings();

        plantings.setId(resultSet.getLong("id"));

        long headInstitutionLandId = resultSet.getLong("headInstitutionLand");
        long cropTypeId = resultSet.getLong("cropType");

        HeadInstitutionLands headInstitutionLand = new HeadInstitutionLandsDao().searchHeadInstitutionLandById(headInstitutionLandId);

        CropTypes cropType = new CropTypesDao().getCropTypesById(cropTypeId);

        plantings.setHeadInstitutionLand(headInstitutionLand);
        plantings.setCropType(cropType);

        plantings.setApproximateHarvestDate(
                resultSet.getObject("approximatedHarvestDate", LocalDateTime.class)
        );

        plantings.setCreatedAt(
                resultSet.getObject("createdAt", LocalDateTime.class)
        );
        plantings.setUpdatedAt(
                resultSet.getObject("updatedAt", LocalDateTime.class)
        );
        plantings.setDeletedAt(
                resultSet.getObject("deletedAt", LocalDateTime.class)
        );

        return plantings;
    }

    private Plantings getPlantingsWithoutClass(ResultSet resultSet) throws SQLException {
        Plantings plantings = new Plantings();
        HeadInstitutionLands headInstitutionLand = new HeadInstitutionLands();
        Lands land = new Lands();

        plantings.setId(resultSet.getLong("id"));

        land.setLandName(resultSet.getString("landName"));

        headInstitutionLand.setLand(land);

        plantings.setHeadInstitutionLand(headInstitutionLand);

        plantings.setTotalVolunteers(resultSet.getInt("totalVolunteers"));
        plantings.setPlantingCosts(resultSet.getDouble("plantingCosts"));
        plantings.setPlantingProfit(resultSet.getDouble("plantingProfit"));
        plantings.setPlantingWeight(resultSet.getDouble("plantingWeight"));

        plantings.setApproximateHarvestDate(
            resultSet.getObject("approximatedHarvestDate", LocalDateTime.class)
        );

        plantings.setCreatedAt(
                resultSet.getObject("createdAt", LocalDateTime.class)
        );

        return plantings;
    }
}
