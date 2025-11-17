/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.controller.helper.ComboBoxHelperVolunteers;
import com.facens.upx2.smartgarden.controller.helper.DialogHelper;
import com.facens.upx2.smartgarden.controller.helper.VolunteerPlantingsControllerHelper;
import com.facens.upx2.smartgarden.model.dao.VolunteersDao;
import com.facens.upx2.smartgarden.model.dao.VolunteersPlantingsDao;
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
    private final ComboBoxHelperVolunteers comboBoxHelperVolunteers;
    
    public VolunteerPlantingsController(VolunteerPlantingsControllerHelper volunteerControllerHelper, PlantingVolunteerScreen volunteerScreen) {
        this.volunteerControllerHelper = volunteerControllerHelper;
        this.volunteerScreen = volunteerScreen;
        this.comboBoxHelperVolunteers = new ComboBoxHelperVolunteers(this.volunteerScreen);
    }
    
    public void loadVolunteers(Long institutionId, Long plantingId){
        VolunteersPlantingsDao volunteersPlantingsDao = new VolunteersPlantingsDao();
        
        List<Users> users = VolunteersPlantingsDao.searchAllVolunteers(institutionId, plantingId);
        
        this.volunteerControllerHelper.populateTable(users);
    }
    
    public void loadVolunteersWithSearch(Long institutionId, String search, Long plantingId){
        VolunteersPlantingsDao volunteersPlantingsDao = new VolunteersPlantingsDao();
        
        List<Users> users = VolunteersPlantingsDao.searchAllVolunteersWithSearch(institutionId, search);
        
        this.volunteerControllerHelper.populateTable(users);
    }
    
    public void loadVolunteersForComboBox(Long institutionId){
        VolunteersDao volunteersDao = new VolunteersDao();
        
        List<Users> volunteers = volunteersDao.searchAllVolunteers(institutionId);
        
        this.comboBoxHelperVolunteers.populateVolunteersComboBox(volunteers);
    }
    
    public void registerVolunteer(Long volunteerId, Long institutionId){      
        
    }
    
    public void deleteSelectedVolunteer(Long institutionId, Long plantingId){
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

        this.loadVolunteers(institutionId, plantingId);
    }
}
