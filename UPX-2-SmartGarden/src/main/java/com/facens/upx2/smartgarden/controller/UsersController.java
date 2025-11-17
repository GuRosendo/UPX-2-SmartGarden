/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.controller.helper.ComboBoxHelper;
import com.facens.upx2.smartgarden.controller.helper.DialogHelper;
import com.facens.upx2.smartgarden.controller.helper.UsersControllerHelper;
import com.facens.upx2.smartgarden.model.dao.CitiesDao;
import com.facens.upx2.smartgarden.model.dao.CountriesDao;
import com.facens.upx2.smartgarden.model.dao.InstitutionsDao;
import com.facens.upx2.smartgarden.model.dao.StatesDao;
import com.facens.upx2.smartgarden.model.dao.UsersDao;
import com.facens.upx2.smartgarden.model.domain.Addresses;
import com.facens.upx2.smartgarden.model.domain.Cities;
import com.facens.upx2.smartgarden.model.domain.Countries;
import com.facens.upx2.smartgarden.model.domain.Institutions;
import com.facens.upx2.smartgarden.model.domain.States;
import com.facens.upx2.smartgarden.model.domain.Users;
import com.facens.upx2.smartgarden.view.form.UsersScreen;
import java.util.List;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class UsersController{
    private final UsersControllerHelper usersControllerHelper;
    private final UsersScreen usersScreen;
    private final ComboBoxHelper comboBoxHelper;
    
    public UsersController(UsersControllerHelper usersControllerHelper, UsersScreen usersScreen) {
        this.usersControllerHelper = usersControllerHelper;
        this.usersScreen = usersScreen;
        this.comboBoxHelper = new ComboBoxHelper(this.usersScreen);
    }
    
    public void loadUsers(Long institutionId){
        UsersDao usersDao = new UsersDao();
        
        List<Users> users = usersDao.searchAllUsers(institutionId);
        
        this.usersControllerHelper.populateTable(users);
    }
    
    public void loadUsersWithSearch(Long institutionId, String search){
        UsersDao usersDao = new UsersDao();
        
        List<Users> users = usersDao.searchAllUsersWithSearch(institutionId, search);
        
        this.usersControllerHelper.populateTable(users);
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
    
    public void registerUser(String name, String userName, String email, String password, String repeatedPassword, String userCep, String neighborhoodName, String streetName, String number, Long cityId, Long institutionId){
        if(name.isEmpty()){
            DialogHelper.showMessage("O campo nome deve ser preenchido", this.usersScreen);
            return;
        }
        
        if(userName.isEmpty()){
            DialogHelper.showMessage("O campo nome do usuário deve ser preenchido", this.usersScreen);
            return;
        }
        
        if(password.isEmpty()){
            DialogHelper.showMessage("O campo senha deve ser preenchido", this.usersScreen);
            return;
        }
        
        if(password.length() < 8){
            DialogHelper.showMessage("O campo senha deve possuir pelo menos 8 caracteres", this.usersScreen);
            return;
        }
        
        if(repeatedPassword.isEmpty()){
            DialogHelper.showMessage("O campo repita a senha deve ser preenchido", this.usersScreen);
            return;
        }
        
        if(!password.equals(repeatedPassword)){
            DialogHelper.showMessage("As senhas não coincidem", this.usersScreen);
            return;
        }
        
        if(email.isEmpty()){
            DialogHelper.showMessage("O campo email deve ser preenchido", this.usersScreen);
            return;
        }
        
        if(userCep.isEmpty() || userCep.length() != 8 || !userCep.matches("\\d{8}")){
            DialogHelper.showMessage("O CEP deve conter exatamente 8 números (sem pontos, hífens ou letras)", this.usersScreen);
            return;
        }
        
        if(neighborhoodName.isEmpty()){
            DialogHelper.showMessage("O campo bairro deve ser preenchido", this.usersScreen);
            return;
        }
        
        if(streetName.isEmpty()){
            DialogHelper.showMessage("O campo rua deve ser preenchido", this.usersScreen);
            return;
        }
        
        if(number.isEmpty() || (!number.matches("\\d+") && !number.equalsIgnoreCase("SN"))){
            DialogHelper.showMessage("O campo número deve conter apenas números ou 'SN' (sem número)", this.usersScreen);
            return;
        }
        
        if(cityId == 0){
            DialogHelper.showMessage("Cidade incorreta", this.usersScreen);
            return;
        }
        
        UsersDao usersDao = new UsersDao();
        
        Users existEmail = usersDao.searchUserByEmail(email);
            
        if(existEmail != null){
            DialogHelper.showMessage("Email já cadastrado!", this.usersScreen);
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
        newUser.setUserName(userName);
        newUser.setUserEmail(email);
        newUser.setUserPassword(password);
        newUser.setIsAdmin(null);
        newUser.setInstitution(institution);
        newUser.setUserAddress(newAddress);
        
        String result = usersDao.save(newUser);

        DialogHelper.showMessage(result, this.usersScreen);

        if (result.toLowerCase().contains("sucesso")) {
            this.loadUsers(institutionId);

            this.usersScreen.clearInputs();
        }
    }
    
    public void deleteSelectedUser(Long institutionId){
        int row = this.usersScreen.getjTable1().getSelectedRow();

        if(row < 0){
            DialogHelper.showMessage("Selecione um usuário na tabela para excluir", this.usersScreen);
            
            return;
        }

        Long userId = (Long) this.usersScreen.getjTable1().getValueAt(row, 0);
        String userEmail = (String) this.usersScreen.getjTable1().getValueAt(row, 1);
        
        UsersDao usersDao = new UsersDao();
        Users isAdmin = usersDao.checkIfUserIsAdmin(userId);

        if(isAdmin != null){
            DialogHelper.showMessage("O usuário administrador não pode ser excluído", this.usersScreen);
            return;
        }

        boolean confirm = DialogHelper.showConfirm(
            "Deseja realmente excluir o usuário: " + userEmail + "?",
            "Confirmar exclusão",
            this.usersScreen
        );

        if(!confirm){
            return;
        }

        String result = new UsersDao().deleteUserById(userId);

        DialogHelper.showMessage(result, this.usersScreen);

        this.loadUsers(institutionId);
    }
}
