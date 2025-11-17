/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller.helper;

import com.facens.upx2.smartgarden.model.domain.VolunteerPlantings;
import com.facens.upx2.smartgarden.view.form.PlantingVolunteerScreen;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class VolunteerPlantingsControllerHelper{
    private final PlantingVolunteerScreen volunteerScreen;

    public VolunteerPlantingsControllerHelper(PlantingVolunteerScreen volunteerScreen){
        this.volunteerScreen = volunteerScreen;
    }
    
    public void populateTable(List<VolunteerPlantings> volunteerPlantigs){
        DefaultTableModel tableModel = (DefaultTableModel) this.volunteerScreen.getjTable1().getModel();

        tableModel.setNumRows(0);
        
        for(VolunteerPlantings volunteerPlantig : volunteerPlantigs){
            tableModel.addRow(new Object[]{
                volunteerPlantig.getId(),
            });
        };
        
        this.volunteerScreen.getjTable1().getColumnModel().getColumn(0).setMinWidth(0);
        this.volunteerScreen.getjTable1().getColumnModel().getColumn(0).setMaxWidth(0);
        this.volunteerScreen.getjTable1().getColumnModel().getColumn(0).setWidth(0);
    }
}
