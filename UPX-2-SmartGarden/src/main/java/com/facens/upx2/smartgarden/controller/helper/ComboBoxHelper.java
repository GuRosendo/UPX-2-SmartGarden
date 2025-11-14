/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller.helper;

import com.facens.upx2.smartgarden.model.domain.Cities;
import com.facens.upx2.smartgarden.model.domain.Countries;
import com.facens.upx2.smartgarden.model.domain.States;
import com.facens.upx2.smartgarden.model.helper.domain.ComboItem;
import com.facens.upx2.smartgarden.view.form.LandsScreen;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class ComboBoxHelper{
    private final LandsScreen landsScreen;
    
    public ComboBoxHelper(LandsScreen landsScreen){
        this.landsScreen = landsScreen;
    }
    
    public void populateCountriesComboBox(List<Countries> countries){
        DefaultComboBoxModel<ComboItem> comboBoxModel = new DefaultComboBoxModel<>();

        comboBoxModel.addElement(new ComboItem(0, "Selecione um país"));

        for(Countries country : countries){
            comboBoxModel.addElement(new ComboItem(country.getId(), country.getNamePt()));
        }

        this.landsScreen.getjCountryComboBox().setModel(comboBoxModel);
    }
    
    public void populateStatesComboBox(List<States> states){
        DefaultComboBoxModel<ComboItem> comboBoxModel = new DefaultComboBoxModel<>();

        comboBoxModel.addElement(new ComboItem(0, "Selecione um estado"));

        for(States state : states){
            comboBoxModel.addElement(new ComboItem(state.getId(), state.getName()));
        }

        this.landsScreen.getjStateComboBox().setModel(comboBoxModel);
    }
    
    public void populateCitiesComboBox(List<Cities> cities){
        DefaultComboBoxModel<ComboItem> comboBoxModel = new DefaultComboBoxModel<>();

        comboBoxModel.addElement(new ComboItem(0, "Selecione uma cidade"));

        for(Cities city : cities){
            comboBoxModel.addElement(new ComboItem(city.getId(), city.getName()));
        }

        this.landsScreen.getjCityComboBox().setModel(comboBoxModel);
    }
}
