/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.controller.helper.ComboBoxHelper;
import com.facens.upx2.smartgarden.controller.helper.DialogHelper;
import com.facens.upx2.smartgarden.controller.helper.VolunteersControllerHelper;
import com.facens.upx2.smartgarden.model.dao.CitiesDao;
import com.facens.upx2.smartgarden.model.dao.CountriesDao;
import com.facens.upx2.smartgarden.model.dao.InstitutionsDao;
import com.facens.upx2.smartgarden.model.dao.StatesDao;
import com.facens.upx2.smartgarden.model.dao.UsersDao;
import com.facens.upx2.smartgarden.model.dao.VolunteersDao;
import com.facens.upx2.smartgarden.model.domain.Addresses;
import com.facens.upx2.smartgarden.model.domain.Cities;
import com.facens.upx2.smartgarden.model.domain.Countries;
import com.facens.upx2.smartgarden.model.domain.Institutions;
import com.facens.upx2.smartgarden.model.domain.States;
import com.facens.upx2.smartgarden.model.domain.Users;
import com.facens.upx2.smartgarden.view.form.VolunteersScreen;
import java.util.List;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class VolunteersController{
    private final VolunteersControllerHelper volunteersControllerHelper;
    private final VolunteersScreen volunteersScreen;
    private final ComboBoxHelper comboBoxHelper;
    
    public VolunteersController(VolunteersControllerHelper volunteersControllerHelper, VolunteersScreen volunteersScreen) {
        this.volunteersControllerHelper = volunteersControllerHelper;
        this.volunteersScreen = volunteersScreen;
        this.comboBoxHelper = new ComboBoxHelper(this.volunteersScreen);
    }
    
    public void loadVolunteers(Long institutionId){
        VolunteersDao volunteersDao = new VolunteersDao();
        
        List<Users> volunteers = volunteersDao.searchAllVolunteers(institutionId);
        
        this.volunteersControllerHelper.populateTable(volunteers);
    }
    
    public void loadVolunteersWithSearch(Long institutionId, String search){
        VolunteersDao volunteersDao = new VolunteersDao();
        
        List<Users> volunteers = volunteersDao.searchAllVolunteersWithSearch(institutionId, search);
        
        this.volunteersControllerHelper.populateTable(volunteers);
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
    
    public void registerVolunteer(String name, String email, String userCep, String neighborhoodName, String streetName, String number, Long cityId, Long institutionId){
        if(name.isEmpty()){
            DialogHelper.showMessage("O campo nome deve ser preenchido", this.volunteersScreen);
            return;
        }
        
        if(email.isEmpty()){
            DialogHelper.showMessage("O campo email deve ser preenchido", this.volunteersScreen);
            return;
        }
        
        if(userCep.isEmpty() || userCep.length() != 8 || !userCep.matches("\\d{8}")){
            DialogHelper.showMessage("O CEP deve conter exatamente 8 números (sem pontos, hífens ou letras)", this.volunteersScreen);
            return;
        }
        
        if(neighborhoodName.isEmpty()){
            DialogHelper.showMessage("O campo bairro deve ser preenchido", this.volunteersScreen);
            return;
        }
        
        if(streetName.isEmpty()){
            DialogHelper.showMessage("O campo rua deve ser preenchido", this.volunteersScreen);
            return;
        }
        
        if(number.isEmpty() || (!number.matches("\\d+") && !number.equalsIgnoreCase("SN"))){
            DialogHelper.showMessage("O campo número deve conter apenas números ou 'SN' (sem número)", this.volunteersScreen);
            return;
        }
        
        if(cityId == 0){
            DialogHelper.showMessage("Cidade incorreta", this.volunteersScreen);
            return;
        }
        
        VolunteersDao volunteersDao = new VolunteersDao();
        
        Users existEmail = volunteersDao.searchVolunteerByEmail(email);
            
        if(existEmail != null){
            DialogHelper.showMessage("Email já cadastrado!", this.volunteersScreen);
            return;
        }
        
        CitiesDao citiesDao = new CitiesDao();
        Cities city = citiesDao.searchCityById(cityId);
        
        Addresses newAddress = new Addresses();
        newAddress.setStreetName(streetName);
        newAddress.setNumber(number);
        newAddress.setNeighborhoodName(neighborhoodName);
        newAddress.setCEP(userCep);
        newAddress.setCity(city);
        newAddress.setState(city.getUf());
        newAddress.setCountry(city.getUf().getCountry());
        newAddress.setType(1);
        
        InstitutionsDao institutionsDao = new InstitutionsDao();
        Institutions institution = institutionsDao.searchInstitutionById(institutionId);
        
        Users newUser = new Users();
        newUser.setFullName(name);
        newUser.setUserEmail(email);
        newUser.setInstitution(institution);
        newUser.setUserAddress(newAddress);
        
        String result = volunteersDao.save(newUser);

        DialogHelper.showMessage(result, this.volunteersScreen);

        if (result.toLowerCase().contains("sucesso")) {
            this.loadVolunteers(institutionId);

            this.volunteersScreen.clearInputs();
        }
    }
    
    public void deleteSelectedUser(Long institutionId){
        int row = this.volunteersScreen.getjTable1().getSelectedRow();

        if(row < 0){
            DialogHelper.showMessage("Selecione um voluntário na tabela para excluir", this.volunteersScreen);
            
            return;
        }

        Long volunteerId = (Long) this.volunteersScreen.getjTable1().getValueAt(row, 0);
        String userEmail = (String) this.volunteersScreen.getjTable1().getValueAt(row, 1);

        boolean confirm = DialogHelper.showConfirm(
            "Deseja realmente excluir o voluntário: " + userEmail + "?",
            "Confirmar exclusão",
            this.volunteersScreen
        );

        if(!confirm){
            return;
        }

        String result = new VolunteersDao().deleteVolunteerById(volunteerId);

        DialogHelper.showMessage(result, this.volunteersScreen);

        this.loadVolunteers(institutionId);
    }
}
