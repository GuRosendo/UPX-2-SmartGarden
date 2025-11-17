/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.controller.helper.CropTypesControllerHelper;
import com.facens.upx2.smartgarden.controller.helper.DialogHelper;
import com.facens.upx2.smartgarden.model.dao.CropTypesDao;
import com.facens.upx2.smartgarden.model.dao.InstitutionsDao;
import com.facens.upx2.smartgarden.model.domain.CropTypes;
import com.facens.upx2.smartgarden.model.domain.Institutions;
import com.facens.upx2.smartgarden.view.form.CropTypesScreen;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class CropTypesController{
    private final CropTypesControllerHelper cropTypesControllerHelper;
    private final CropTypesScreen cropTypesScreen;
    
    public CropTypesController(CropTypesControllerHelper cropTypesControllerHelper, CropTypesScreen cropTypesScreen) {
        this.cropTypesControllerHelper = cropTypesControllerHelper;
        this.cropTypesScreen = cropTypesScreen;
    }
    
    public void loadCropTypes(Long institutionId){
        CropTypesDao cropTypesDao = new CropTypesDao();
        
        List<CropTypes> cropTypes = cropTypesDao.getCropTypes(institutionId);
        
        this.cropTypesControllerHelper.populateTable(cropTypes);
    }
    
    public void loadCropTypesWithSearch(Long institutionId, String search){
        CropTypesDao cropTypesDao = new CropTypesDao();
        
        List<CropTypes> cropTypes = cropTypesDao.getCropTypesWithSearch(institutionId, search);
        
        this.cropTypesControllerHelper.populateTable(cropTypes);
    }
    
    public void registerCropType(String name, String startDate, String endDate, Long institutionId){
        if(name.isEmpty()){
            DialogHelper.showMessage("O campo nome deve ser preenchido", this.cropTypesScreen);
            return;
        }
        
        if(startDate.isEmpty()){
            DialogHelper.showMessage("O campo data inicial de plantio apropriada deve ser preenchido", this.cropTypesScreen);
            return;
        }
        
        if(endDate.isEmpty()){
            DialogHelper.showMessage("O campo data final de plantio apropriada deve ser preenchido", this.cropTypesScreen);
            return;
        }
        
        DateTimeFormatter brFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime start;
        LocalDateTime end;

        try{
            start = LocalDate.parse(startDate, brFormatter).atStartOfDay();
            end = LocalDate.parse(endDate, brFormatter).atStartOfDay();
        }catch(Exception e){
            DialogHelper.showMessage("Formato de data inválido! Use: dd/MM/yyyy", this.cropTypesScreen);
            return;
        }

        if(start.isAfter(end)){
            DialogHelper.showMessage("A data inicial não pode ser maior que a data final.", this.cropTypesScreen);
            return;
        }
        
        InstitutionsDao institutionsDao = new InstitutionsDao();
        Institutions institution = institutionsDao.searchInstitutionById(institutionId);
        
        CropTypes newCropType = new CropTypes();
        newCropType.setInstitution(institution);
        newCropType.setName(name);
        newCropType.setSeedingDateStart(start);
        newCropType.setSeedingDateEnd(end);
        newCropType.setCreatedAt(LocalDateTime.now());
        newCropType.setDeletedAt(null);
        
        CropTypesDao cropTypesDao = new CropTypesDao();
        
        String result = cropTypesDao.save(newCropType);

        DialogHelper.showMessage(result, this.cropTypesScreen);

        if(result.toLowerCase().contains("sucesso")){
            this.cropTypesScreen.clearInputs();
            this.loadCropTypes(institutionId);
        }
    }
    
    public void deleteSelectedCropType(Long institutionId){
        int row = this.cropTypesScreen.getTableCropTypes().getSelectedRow();

        if(row < 0){
            DialogHelper.showMessage("Selecione um tipo de plantio na tabela para excluir", this.cropTypesScreen);
            
            return;
        }

        Long cropTypeId = (Long) this.cropTypesScreen.getTableCropTypes().getValueAt(row, 0);
        String cropTypeName = (String) this.cropTypesScreen.getTableCropTypes().getValueAt(row, 1);

        boolean confirm = DialogHelper.showConfirm(
            "Deseja realmente excluir o tipo de plantio: " + cropTypeName + "?",
            "Confirmar exclusão",
            this.cropTypesScreen
        );

        if(!confirm){
            return;
        }

        String result = new CropTypesDao().deleteCropTypeById(cropTypeId);

        DialogHelper.showMessage(result, this.cropTypesScreen);

        this.loadCropTypes(institutionId);
    }
}
