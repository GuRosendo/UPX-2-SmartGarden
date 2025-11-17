/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller.helper;

import com.facens.upx2.smartgarden.model.domain.Users;
import com.facens.upx2.smartgarden.view.form.UsersScreen;
import com.facens.upx2.smartgarden.view.form.VolunteersScreen;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class VolunteersControllerHelper{
    private final VolunteersScreen volunteersScreen;

    public VolunteersControllerHelper(VolunteersScreen volunteersScreen){
        this.volunteersScreen = volunteersScreen;
    }
    
    public void populateTable(List<Users> users){
        DefaultTableModel tableModel = (DefaultTableModel) this.volunteersScreen.getjTable1().getModel();

        tableModel.setNumRows(0);
        
        for(Users user : users){
            tableModel.addRow(new Object[]{
                user.getId(),
                user.getUserEmail(),
                user.getUserName(),
                user.getFullName(),
                user.getUserAddress().getCEP(),
                user.getUserAddress().getCity().getName(),
                user.getUserAddress().getNeighborhoodName(),
                user.getUserAddress().getStreetName(),
                user.getUserAddress().getNumber(),
                user.getCreatedAtFormatted() + " " + user.getCreatedAtHourFormatted()
            });
        };
        
        this.volunteersScreen.getjTable1().getColumnModel().getColumn(0).setMinWidth(0);
        this.volunteersScreen.getjTable1().getColumnModel().getColumn(0).setMaxWidth(0);
        this.volunteersScreen.getjTable1().getColumnModel().getColumn(0).setWidth(0);
    }
}
