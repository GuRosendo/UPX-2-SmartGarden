/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller.helper;

import com.facens.upx2.smartgarden.model.domain.CropTypes;
import com.facens.upx2.smartgarden.view.form.CropTypesScreen;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class CropTypesControllerHelper{
    private final CropTypesScreen cropTypesScreen;

    public CropTypesControllerHelper(CropTypesScreen cropTypesScreen){
        this.cropTypesScreen = cropTypesScreen;
    }
    
    public void populateTable(List<CropTypes> cropTypes){
        DefaultTableModel tableModel = (DefaultTableModel) this.cropTypesScreen.getTableCropTypes().getModel();

        tableModel.setNumRows(0);
        
        for(CropTypes cropType : cropTypes){
            tableModel.addRow(new Object[]{
                cropType.getId(),
                cropType.getName(),
                cropType.getSeedingDateStartFormatted(),
                cropType.getSeedingDateEndFormatted(),
                cropType.getCreatedAtFormatted() + " " + cropType.getCreatedAtHourFormatted()
            });
        };
        
        this.cropTypesScreen.getTableCropTypes().getColumnModel().getColumn(0).setMinWidth(0);
        this.cropTypesScreen.getTableCropTypes().getColumnModel().getColumn(0).setMaxWidth(0);
        this.cropTypesScreen.getTableCropTypes().getColumnModel().getColumn(0).setWidth(0);
    }
}
