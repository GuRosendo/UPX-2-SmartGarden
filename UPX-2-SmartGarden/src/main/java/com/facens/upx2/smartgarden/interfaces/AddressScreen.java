/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.facens.upx2.smartgarden.interfaces;

import com.facens.upx2.smartgarden.model.helper.domain.ComboItem;
import javax.swing.JComboBox;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public interface AddressScreen{
    JComboBox<ComboItem> getjCountryComboBox();
    JComboBox<ComboItem> getjStateComboBox();
    JComboBox<ComboItem> getjCityComboBox();
}
