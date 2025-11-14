/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.Addresses;
import com.facens.upx2.smartgarden.model.domain.HeadInstitutionLands;
import com.facens.upx2.smartgarden.model.domain.Institutions;
import com.facens.upx2.smartgarden.model.domain.Lands;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}