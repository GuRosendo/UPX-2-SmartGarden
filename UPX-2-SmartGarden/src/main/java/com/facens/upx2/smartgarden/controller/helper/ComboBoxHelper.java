/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller.helper;

import com.facens.upx2.smartgarden.interfaces.AddressScreen;
import com.facens.upx2.smartgarden.model.domain.Cities;
import com.facens.upx2.smartgarden.model.domain.Countries;
import com.facens.upx2.smartgarden.model.domain.States;
import com.facens.upx2.smartgarden.model.helper.domain.ComboItem;
import java.util.List;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class ComboBoxHelper{
    private final AddressScreen screen;
    
    public ComboBoxHelper(AddressScreen screen){
        this.screen = screen;
    }
    
    public void populateCountriesComboBox(List<Countries> countries){
        DefaultComboBoxModel<ComboItem> comboBoxModel = new DefaultComboBoxModel<>();

        comboBoxModel.addElement(new ComboItem(0, ""));

        for(Countries country : countries){
            comboBoxModel.addElement(new ComboItem(country.getId(), country.getNamePt()));
        }

        this.screen.getjCountryComboBox().setModel(comboBoxModel);
    }
    
    public void populateStatesComboBox(List<States> states){
        DefaultComboBoxModel<ComboItem> comboBoxModel = new DefaultComboBoxModel<>();

        comboBoxModel.addElement(new ComboItem(0, ""));

        for(States state : states){
            comboBoxModel.addElement(new ComboItem(state.getId(), state.getName()));
        }

        this.screen.getjStateComboBox().setModel(comboBoxModel);
    }
    
    public void populateCitiesComboBox(List<Cities> cities){
        DefaultComboBoxModel<ComboItem> comboBoxModel = new DefaultComboBoxModel<>();

        comboBoxModel.addElement(new ComboItem(0, ""));

        for(Cities city : cities){
            comboBoxModel.addElement(new ComboItem(city.getId(), city.getName()));
        }

        this.screen.getjCityComboBox().setModel(comboBoxModel);
    }
}
