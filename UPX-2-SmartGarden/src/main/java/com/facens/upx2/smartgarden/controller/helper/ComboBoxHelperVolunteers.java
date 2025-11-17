/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller.helper;

import com.facens.upx2.smartgarden.model.domain.Users;
import com.facens.upx2.smartgarden.model.helper.domain.ComboItem;
import com.facens.upx2.smartgarden.view.form.PlantingVolunteerScreen;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class ComboBoxHelperVolunteers{
    private final PlantingVolunteerScreen plantingVolunteerScreen;
    
    public ComboBoxHelperVolunteers(PlantingVolunteerScreen plantingVolunteerScreen){
        this.plantingVolunteerScreen = plantingVolunteerScreen;
    }
    
    public void populateVolunteersComboBox(List<Users> volunteers){
        DefaultComboBoxModel<ComboItem> comboBoxModel = new DefaultComboBoxModel<>();

        comboBoxModel.addElement(new ComboItem(0, ""));

        for(Users volunteer : volunteers){
            comboBoxModel.addElement(new ComboItem(volunteer.getId(), volunteer.getFullName()));
        }

        this.plantingVolunteerScreen.getjComboBoxVolunteers().setModel(comboBoxModel);
    }
}
