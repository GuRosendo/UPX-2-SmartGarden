/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller.helper;

import com.facens.upx2.smartgarden.model.domain.Plantings;
import com.facens.upx2.smartgarden.view.form.PlantingsScreen;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class PlantingsControllerHelper {
    private final PlantingsScreen plantingsScreen;

    public PlantingsControllerHelper(PlantingsScreen plantingsScreen){
        this.plantingsScreen = plantingsScreen;
    }
    
    public void populateTable(List<Plantings> plantings){
        DefaultTableModel tableModel = (DefaultTableModel) this.plantingsScreen.getjTable1().getModel();

        tableModel.setNumRows(0);
        
        for(Plantings planting : plantings){
            tableModel.addRow(new Object[]{
                
            });
        };
        
        this.plantingsScreen.getjTable1().getColumnModel().getColumn(0).setMinWidth(0);
        this.plantingsScreen.getjTable1().getColumnModel().getColumn(0).setMaxWidth(0);
        this.plantingsScreen.getjTable1().getColumnModel().getColumn(0).setWidth(0);
    }
}
