/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller.helper;

import com.facens.upx2.smartgarden.model.domain.CropTypes;
import com.facens.upx2.smartgarden.model.domain.Lands;
import com.facens.upx2.smartgarden.model.helper.domain.ComboItem;
import com.facens.upx2.smartgarden.view.form.PlantingsScreen;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class ComboBoxHelperPlantings{
    private final PlantingsScreen plantingsScreen;
    
    public ComboBoxHelperPlantings(PlantingsScreen plantingsScreen){
        this.plantingsScreen = plantingsScreen;
    }
    
    public void populateLandsComboBox(List<Lands> lands){
        DefaultComboBoxModel<ComboItem> comboBoxModel = new DefaultComboBoxModel<>();

        comboBoxModel.addElement(new ComboItem(0, ""));

        for(Lands land : lands){
            comboBoxModel.addElement(new ComboItem(land.getId(), land.getLandName()));
        }

        this.plantingsScreen.getjComboBoxLands().setModel(comboBoxModel);
    }
    
    public void populateCropTypesComboBox(List<CropTypes> cropTypes){
        DefaultComboBoxModel<ComboItem> comboBoxModel = new DefaultComboBoxModel<>();

        comboBoxModel.addElement(new ComboItem(0, ""));

        for(CropTypes cropType : cropTypes){
            comboBoxModel.addElement(new ComboItem(cropType.getId(), cropType.getName()));
        }

        this.plantingsScreen.getjCropTypeComboBox().setModel(comboBoxModel);
    }
}
