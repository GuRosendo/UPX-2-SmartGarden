/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.dao;

import com.facens.upx2.smartgarden.model.database.connection.DatabaseConnection;
import com.facens.upx2.smartgarden.model.database.connection.MySQLDatabaseConnection;
import com.facens.upx2.smartgarden.model.domain.Addresses;
import com.facens.upx2.smartgarden.model.domain.Cities;
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
public class LandsDao{
    private final DatabaseConnection databaseConnection;

    public LandsDao(){
        this.databaseConnection = new MySQLDatabaseConnection();
    }
    
    public String save(Lands land){
        return (land.getId() == null || land.getId() == 0L) ? add(land) : edit(land);
    }
    
    private String add(Lands land){
        Addresses address = land.getLandAddress();

        if(address == null){
            return "Erro: endereço não informado";
        }

        String resultAddress = new AddressesDao().save(address);

        if(!resultAddress.contains("sucesso")){
            return resultAddress;
        }

        String queryAddLand = "INSERT INTO lands(landAddress, landName) VALUES (?, ?)";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false); 

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryAddLand, PreparedStatement.RETURN_GENERATED_KEYS)){
                preparedStatement.setLong(1, address.getId());
                preparedStatement.setString(2, land.getLandName());

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                    
                    if(generatedKeys.next()){
                        land.setId(generatedKeys.getLong(1));
                    }

                    connection.commit();
                    
                    return "Terreno adicionado com sucesso";
                }else{
                    connection.rollback();
                    return "Erro ao adicionar o terreno";
                }

            }catch(SQLException e){
                connection.rollback();
                return "Erro SQL ao adicionar terreno: " + e.getMessage();
            }
        }catch(SQLException e){
            return "Erro ao conectar ou finalizar transação: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    private String edit(Lands land){
        Addresses address = land.getLandAddress();

        if(address == null){
            return "Erro: endereço não informado";
        }

        String resultAddress = new AddressesDao().save(address);

        if(!resultAddress.contains("sucesso")){
            return resultAddress;
        }

        String queryEditLand = "UPDATE lands SET landAddress = ?, landName = ? WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection()){
            connection.setAutoCommit(false);

            try(PreparedStatement preparedStatement = connection.prepareStatement(queryEditLand)){
                preparedStatement.setLong(1, address.getId());
                preparedStatement.setString(2, land.getLandName());
                preparedStatement.setLong(3, land.getId());

                int result = preparedStatement.executeUpdate();

                if(result == 1){
                    connection.commit();
                    
                    return "Terreno editado com sucesso";
                }else{
                    connection.rollback();
                    return "Erro ao editar o terreno";
                }

            }catch(SQLException e){
                connection.rollback();
                return "Erro SQL ao editar terreno: " + e.getMessage();
            }
        }catch(SQLException e){
            return "Erro ao conectar ou finalizar transação: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }

    public List<Lands> getInstitutionLands(Long institutionId){
       String querySearch = "SELECT " +
        "    land.id AS id, " +
        "    land.landName AS landName, " +
        "    land.createdAt AS createdAt, " +
        "    land.updatedAt AS updatedAt, " +
        "    land.deletedAt AS deletedAt, " +
        "    addr.id AS landAddress, " +
        "    addr.cep AS cep, " +
        "    addr.neighborhoodName AS neighborhoodName, " +
        "    addr.streetName AS streetName, " +
        "    addr.number AS number, " +
        "    city.id AS cityId, " +
        "    city.name AS cityName " +
        "FROM lands land " +
        "INNER JOIN headInstitutionLands head ON land.id = head.land " +
        "INNER JOIN addresses addr ON land.landAddress = addr.id " +
        "INNER JOIN cities city ON addr.city = city.id " +
        "WHERE head.institution = ? " +
        "   AND head.deletedAt IS NULL " +
        "   AND land.deletedAt IS NULL " +
        "   AND addr.deletedAt IS NULL " +
        "   AND addr.type = 3 " +
        "ORDER BY land.landName ASC";
        List<Lands> landsList = new ArrayList<>();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setLong(1, institutionId);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                landsList.add(getLandWithoutClass(resultSet));
            }

        }catch(SQLException e){
            System.err.println("Erro ao listar terrenos: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return landsList;
   }
    
    public List<Lands> getInstitutionLandsWithSearch(Long institutionId, String search){
       String querySearch = "SELECT " +
        "    land.id AS id, " +
        "    land.landName AS landName, " +
        "    land.createdAt AS createdAt, " +
        "    land.updatedAt AS updatedAt, " +
        "    land.deletedAt AS deletedAt, " +
        "    addr.id AS landAddress, " +
        "    addr.cep AS cep, " +
        "    addr.neighborhoodName AS neighborhoodName, " +
        "    addr.streetName AS streetName," +
        "    addr.number AS number, " +
        "    city.id AS cityId, " +
        "    city.name AS cityName " +
        "FROM lands land " +
        "INNER JOIN headInstitutionLands head ON land.id = head.land " +
        "INNER JOIN addresses addr ON land.landAddress = addr.id " +
        "INNER JOIN cities city ON addr.city = city.id " +
        "WHERE land.landName LIKE ? " +
        "  AND head.institution = ? " +
        "  AND head.deletedAt IS NULL " +
        "  AND land.deletedAt IS NULL " +
        "ORDER BY land.landName ASC";
        List<Lands> landsList = new ArrayList<>();

        try(Connection connection = databaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(querySearch)){
            preparedStatement.setString(1, search + "%");
            preparedStatement.setLong(2, institutionId);
            
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                landsList.add(getLandWithoutClass(resultSet));
            }

        }catch(SQLException e){
            System.err.println("Erro ao listar terrenos: " + e.getMessage());
        }finally{
            databaseConnection.closeConnection();
        }

        return landsList;
    }
   
    private Lands getLand(ResultSet resultSet) throws SQLException{
        Lands lands = new Lands();

        lands.setId(resultSet.getLong("id"));

        long addressId = resultSet.getLong("landAddress");

        Addresses address = new AddressesDao().searchAddressById(addressId);

        lands.setLandName(resultSet.getString("landName"));
        lands.setLandAddress(address);
        lands.setCreatedAt(resultSet.getObject("createdAt", LocalDateTime.class));
        lands.setUpdatedAt(resultSet.getObject("updatedAt", LocalDateTime.class));
        lands.setDeletedAt(resultSet.getObject("deletedAt", LocalDateTime.class));

        return lands;
    }
    
    public String deleteLandById(Long landId){
        String query = "UPDATE lands SET deletedAt = NOW() WHERE id = ?";

        try(Connection connection = databaseConnection.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query)){

            preparedStatement.setLong(1, landId);

            int result = preparedStatement.executeUpdate();

            if(result == 1){
                return "Terreno excluído com sucesso.";
            }else{
                return "Erro: terreno não encontrado.";
            }

        }catch(SQLException e){
            return "Erro SQL ao excluir terreno: " + e.getMessage();
        }finally{
            databaseConnection.closeConnection();
        }
    }
    
    private Lands getLandWithoutClass(ResultSet resultSet) throws SQLException{
        Lands lands = new Lands();
        Addresses address = new Addresses();
        Cities city = new Cities();

        lands.setId(resultSet.getLong("id"));
        lands.setLandName(resultSet.getString("landName"));

        address.setId(resultSet.getLong("landAddress"));
        address.setCEP(resultSet.getString("cep"));
        address.setNeighborhoodName(resultSet.getString("neighborhoodName"));
        address.setStreetName(resultSet.getString("streetName"));
        address.setNumber(resultSet.getString("number"));

        city.setId(resultSet.getLong("cityId"));
        city.setName(resultSet.getString("cityName"));
        address.setCity(city);

        lands.setLandAddress(address);

        lands.setCreatedAt(resultSet.getObject("createdAt", LocalDateTime.class));
        lands.setUpdatedAt(resultSet.getObject("updatedAt", LocalDateTime.class));
        lands.setDeletedAt(resultSet.getObject("deletedAt", LocalDateTime.class));

        return lands;
    }
}
