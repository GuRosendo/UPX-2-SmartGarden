/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller.helper;

import com.facens.upx2.smartgarden.model.domain.Lands;
import com.facens.upx2.smartgarden.view.form.LandsScreen;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class LandsControllerHelper{
    private final LandsScreen landsScreen;

    public LandsControllerHelper(LandsScreen landsScreen){
        this.landsScreen = landsScreen;
    }
    
    public void populateTable(List<Lands> lands){
        DefaultTableModel tableModel = (DefaultTableModel) this.landsScreen.getTableLands().getModel();

        tableModel.setNumRows(0);
        
        for(Lands land : lands){
            tableModel.addRow(new Object[]{
                land.getId(),
                land.getLandName(),
                land.getLandAddress().getCity().getName(),
                land.getLandAddress().getCEP(),
                land.getLandAddress().getNeighborhoodName(),
                land.getLandAddress().getStreetName(),
                land.getLandAddress().getNumber(),
                land.getCreatedAtFormatted() + " " + land.getCreatedAtHourFormatted()
            });
        };
        
        this.landsScreen.getTableLands().getColumnModel().getColumn(0).setMinWidth(0);
        this.landsScreen.getTableLands().getColumnModel().getColumn(0).setMaxWidth(0);
        this.landsScreen.getTableLands().getColumnModel().getColumn(0).setWidth(0);
    }
}
