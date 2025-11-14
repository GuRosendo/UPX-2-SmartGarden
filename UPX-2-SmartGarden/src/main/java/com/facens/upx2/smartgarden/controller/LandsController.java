/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.controller.helper.ComboBoxHelper;
import com.facens.upx2.smartgarden.controller.helper.LandsControllerHelper;
import com.facens.upx2.smartgarden.model.dao.CitiesDao;
import com.facens.upx2.smartgarden.model.dao.CountriesDao;
import com.facens.upx2.smartgarden.model.dao.HeadInstitutionLandsDao;
import com.facens.upx2.smartgarden.model.dao.InstitutionsDao;
import com.facens.upx2.smartgarden.model.dao.LandsDao;
import com.facens.upx2.smartgarden.model.dao.StatesDao;
import com.facens.upx2.smartgarden.model.domain.Addresses;
import com.facens.upx2.smartgarden.model.domain.Cities;
import com.facens.upx2.smartgarden.model.domain.Countries;
import com.facens.upx2.smartgarden.model.domain.HeadInstitutionLands;
import com.facens.upx2.smartgarden.model.domain.Institutions;
import com.facens.upx2.smartgarden.model.domain.Lands;
import com.facens.upx2.smartgarden.model.domain.States;
import com.facens.upx2.smartgarden.controller.helper.DialogHelper;
import com.facens.upx2.smartgarden.view.form.LandsScreen;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class LandsController{
    private final LandsControllerHelper landsControllerHelper;
    private final ComboBoxHelper comboBoxHelper;
    private final LandsScreen landsScreen;
    
    public LandsController(LandsControllerHelper landsControllerHelper, LandsScreen landsScreen) {
        this.landsControllerHelper = landsControllerHelper;
        this.landsScreen = landsScreen;
        this.comboBoxHelper = new ComboBoxHelper(this.landsScreen);
    }
    
    public void loadLands(Long institutionId){
        LandsDao landsDao = new LandsDao();
        
        List<Lands> lands = landsDao.getInstitutionLands(institutionId);
        
        this.landsControllerHelper.populateTable(lands);
    }
    
    public void loadLandsWithSearch(Long institutionId, String search){
        LandsDao landsDao = new LandsDao();
        
        List<Lands> lands = landsDao.getInstitutionLandsWithSearch(institutionId, search);
        
        this.landsControllerHelper.populateTable(lands);
    }
    
    public void loadCountries(){
        CountriesDao countriesDao = new CountriesDao();
        
        List<Countries> countries = countriesDao.searchAllCountries();
        
        this.comboBoxHelper.populateCountriesComboBox(countries);
    }
    
    public void loadStatesByCountryId(Long countryId){
        StatesDao statesDao = new StatesDao();
        
        List<States> states = statesDao.searchStatesByCountryId(countryId);
        
        this.comboBoxHelper.populateStatesComboBox(states);
    }
    
    public void loadCitiesByStateId(Long stateId){
        CitiesDao citiesDao = new CitiesDao();
                
        List<Cities> cities = citiesDao.searchCitiesByStateId(stateId);
        
        this.comboBoxHelper.populateCitiesComboBox(cities);
    }
    
    public void registerLand(String landName, String landCep, String neighborhoodName, String streetName, String number, Long cityId, Long institutionId){
        if(landName.isEmpty()){
            DialogHelper.showMessage("O campo nome deve ser preenchido", this.landsScreen);
            return;
        }
        
        if(landCep.isEmpty() || landCep.length() != 8 || !landCep.matches("\\d{8}")){
            DialogHelper.showMessage("O CEP deve conter exatamente 8 números (sem pontos, hífens ou letras)", this.landsScreen);
            return;
        }
        
        if(neighborhoodName.isEmpty()){
            DialogHelper.showMessage("O campo bairro deve ser preenchido", this.landsScreen);
            return;
        }
        
        if(streetName.isEmpty()){
            DialogHelper.showMessage("O campo rua deve ser preenchido", this.landsScreen);
            return;
        }
        
        if(number.isEmpty() || (!number.matches("\\d+") && !number.equalsIgnoreCase("SN"))){
            DialogHelper.showMessage("O campo número deve conter apenas números ou 'SN' (sem número)", this.landsScreen);
            return;
        }
        
        if(cityId == 0){
            DialogHelper.showMessage("Cidade incorreta", this.landsScreen);
            return;
        }
        
        CitiesDao citiesDao = new CitiesDao();
        Cities city = citiesDao.searchCityById(cityId);
        
        Addresses newAddress = new Addresses();
        newAddress.setStreetName(streetName);
        newAddress.setNumber(number);
        newAddress.setNeighborhoodName(neighborhoodName);
        newAddress.setCEP(landCep);
        newAddress.setCity(city);
        newAddress.setState(city.getUf());
        newAddress.setCountry(city.getUf().getCountry());
        newAddress.setType(3);
        
        Lands newLand = new Lands();
        newLand.setLandName(landName);
        newLand.setLandAddress(newAddress);
        newLand.setCreatedAt(LocalDateTime.now());
        newLand.setDeletedAt(null);
        
        InstitutionsDao institutionsDao = new InstitutionsDao();
        Institutions institution = institutionsDao.searchInstitutionById(institutionId);
        
        HeadInstitutionLands newHeadInstitutionLand = new HeadInstitutionLands();
        newHeadInstitutionLand.setLand(newLand);
        newHeadInstitutionLand.setInstitution(institution);
        
        LandsDao landsDao = new LandsDao();
        String result = landsDao.save(newLand);

        DialogHelper.showMessage(result, this.landsScreen);

        if (result.toLowerCase().contains("sucesso")) {
            HeadInstitutionLandsDao headInstitutionLandsDao = new HeadInstitutionLandsDao();
            String linkResult = headInstitutionLandsDao.save(newHeadInstitutionLand);

            if(!linkResult.toLowerCase().contains("sucesso")){
                DialogHelper.showMessage("Terreno salvo, mas ocorreu um erro ao vincular à instituição: " + linkResult, this.landsScreen);
            }else{
                this.loadLands(institutionId);
                this.landsScreen.clearInputs();
            }
        }
    }
    
    public void deleteSelectedLand(Long institutionId){
        int row = this.landsScreen.getTableLands().getSelectedRow();

        if(row < 0){
            DialogHelper.showMessage("Selecione um terreno na tabela para excluir", this.landsScreen);
            
            return;
        }

        Long landId = (Long) this.landsScreen.getTableLands().getValueAt(row, 0);
        String landName = (String) this.landsScreen.getTableLands().getValueAt(row, 1);

        boolean confirm = DialogHelper.showConfirm(
            "Deseja realmente excluir o terreno: " + landName + "?",
            "Confirmar exclusão",
            this.landsScreen
        );

        if(!confirm){
            return;
        }

        String result = new LandsDao().deleteLandById(landId);

        DialogHelper.showMessage(result, this.landsScreen);

        this.loadLands(institutionId);
    }
}
