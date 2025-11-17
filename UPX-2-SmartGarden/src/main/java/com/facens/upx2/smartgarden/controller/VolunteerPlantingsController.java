/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.controller.helper.ComboBoxHelper;
import com.facens.upx2.smartgarden.controller.helper.DialogHelper;
import com.facens.upx2.smartgarden.controller.helper.VolunteerPlantingsControllerHelper;
import com.facens.upx2.smartgarden.model.dao.CitiesDao;
import com.facens.upx2.smartgarden.model.dao.CountriesDao;
import com.facens.upx2.smartgarden.model.dao.CropTypesDao;
import com.facens.upx2.smartgarden.model.dao.InstitutionsDao;
import com.facens.upx2.smartgarden.model.dao.StatesDao;
import com.facens.upx2.smartgarden.model.dao.UsersDao;
import com.facens.upx2.smartgarden.model.dao.VolunteersPlantingsDao;
import com.facens.upx2.smartgarden.model.domain.Addresses;
import com.facens.upx2.smartgarden.model.domain.Cities;
import com.facens.upx2.smartgarden.model.domain.Countries;
import com.facens.upx2.smartgarden.model.domain.Institutions;
import com.facens.upx2.smartgarden.model.domain.States;
import com.facens.upx2.smartgarden.model.domain.Users;
import com.facens.upx2.smartgarden.model.domain.VolunteerPlantings;
import com.facens.upx2.smartgarden.view.form.PlantingVolunteerScreen;
import java.util.List;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class VolunteerPlantingsController{
    private final VolunteerPlantingsControllerHelper volunteerControllerHelper;
    private final PlantingVolunteerScreen volunteerScreen;
    private final ComboBoxHelper comboBoxHelper;
    
    public VolunteerPlantingsController(VolunteerPlantingsControllerHelper volunteerControllerHelper, PlantingVolunteerScreen volunteerScreen) {
        this.volunteerControllerHelper = volunteerControllerHelper;
        this.volunteerScreen = volunteerScreen;
        this.comboBoxHelper = new ComboBoxHelper(this.volunteerScreen);
    }
    
    public void loadVolunteers(Long institutionId){
        VolunteersPlantingsDao volunteersPlantingsDao = new VolunteersPlantingsDao();
        
        //List<Users> users = VolunteersPlantingsDao.searchAllVolunteers(institutionId);
        
        //this.volunteerControllerHelper.populateTable(users);
    }
    
    public void loadVolunteersWithSearch(Long institutionId, String search){
        VolunteersPlantingsDao volunteersPlantingsDao = new VolunteersPlantingsDao();
        
        //List<VolunteerPlantings> volunteerPlantings = VolunteersPlantingsDao.searchAllVolunteersWithSearch(institutionId, search);
        
       // this.volunteerControllerHelper.populateTable(volunteerPlantings);
    }
    
    public void registerVolunteer(Long volunteerId, Long institutionId){      
        
    }
    
    public void deleteSelectedVolunteer(Long institutionId){
        int row = this.volunteerScreen.getjTable1().getSelectedRow();

        if(row < 0){
            DialogHelper.showMessage("Selecione um voluntário na tabela para excluir", this.volunteerScreen);
            
            return;
        }

        Long volunteerId = (Long) this.volunteerScreen.getjTable1().getValueAt(row, 0);
        String volunteerName = (String) this.volunteerScreen.getjTable1().getValueAt(row, 1);

        boolean confirm = DialogHelper.showConfirm(
            "Deseja realmente excluir o voluntário: " + volunteerName + "?",
            "Confirmar exclusão",
            this.volunteerScreen
        );

        if(!confirm){
            return;
        }

        String result = new VolunteersPlantingsDao().deleteVolunteerById(volunteerId);

        DialogHelper.showMessage(result, this.volunteerScreen);

        this.loadVolunteers(institutionId);
    }
}
