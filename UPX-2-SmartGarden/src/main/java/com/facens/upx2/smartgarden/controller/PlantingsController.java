/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.controller.helper.ComboBoxHelperPlantings;
import com.facens.upx2.smartgarden.controller.helper.DialogHelper;
import com.facens.upx2.smartgarden.controller.helper.PlantingsControllerHelper;
import com.facens.upx2.smartgarden.model.dao.CropTypesDao;
import com.facens.upx2.smartgarden.model.dao.HeadInstitutionLandsDao;
import com.facens.upx2.smartgarden.model.dao.LandsDao;
import com.facens.upx2.smartgarden.model.dao.PlantingsDao;
import com.facens.upx2.smartgarden.model.domain.CropTypes;
import com.facens.upx2.smartgarden.model.domain.HeadInstitutionLands;
import com.facens.upx2.smartgarden.model.domain.Lands;
import com.facens.upx2.smartgarden.model.domain.Plantings;
import com.facens.upx2.smartgarden.view.form.PlantingsScreen;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class PlantingsController {
    private final PlantingsControllerHelper plantingsControllerHelper;
    private final ComboBoxHelperPlantings comboBoxHelperLands;
    private final PlantingsScreen plantingsScreen;
    
    public PlantingsController(PlantingsControllerHelper plantingsControllerHelper, PlantingsScreen plantingsScreen){
        this.plantingsControllerHelper = plantingsControllerHelper;
        this.plantingsScreen = plantingsScreen;
        this.comboBoxHelperLands = new ComboBoxHelperPlantings(this.plantingsScreen);
    }
    
    public void loadPlantings(Long institutionId){
        PlantingsDao plantingsDao = new PlantingsDao();
        
        List<Plantings> plantings = plantingsDao.getPlantings(institutionId);
        
        this.plantingsControllerHelper.populateTable(plantings);
    }
    
    public void loadPlantingsWithSearch(Long institutionId, String search){
        PlantingsDao plantingsDao = new PlantingsDao();
        
        List<Plantings> plantings = plantingsDao.getPlantingsWithSearch(institutionId, search);
        
        this.plantingsControllerHelper.populateTable(plantings);
    }
    
    public void loadLands(Long institutionId){
        LandsDao landsDao = new LandsDao();
        
        List<Lands> lands = landsDao.getInstitutionLands(institutionId);
        
        this.comboBoxHelperLands.populateLandsComboBox(lands);
    }
    
    public void loadCropTypes(Long institutionId){
        CropTypesDao cropTypesDao = new CropTypesDao();
        
        List<CropTypes> cropTypes = cropTypesDao.getCropTypes(institutionId);
        
        this.comboBoxHelperLands.populateCropTypesComboBox(cropTypes);
    }
    
    public void registerPlanting(String approximatedHarvestDate, Long landId, Long cropTypeId, Long institutionId){
        if(approximatedHarvestDate == null || approximatedHarvestDate.isEmpty()){
            DialogHelper.showMessage("A data aproximada da colheita é obrigatória", this.plantingsScreen);
            return;
        }

        DateTimeFormatter brFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDateTime approximated;

        try{
            approximated = LocalDate.parse(approximatedHarvestDate, brFormatter).atStartOfDay();
        }catch(Exception e){
            DialogHelper.showMessage("Formato de data inválido! Use: dd/MM/yyyy", this.plantingsScreen);
            return;
        }
        
        if(approximated.toLocalDate().isBefore(LocalDate.now())){
            DialogHelper.showMessage("A aproximada da colheita não pode ser anterior ao dia de hoje", this.plantingsScreen);
            return;
        }
        
        if(cropTypeId == 0){
            DialogHelper.showMessage("Tipo de plantio inválido", this.plantingsScreen);
            return;
        }
        
        HeadInstitutionLandsDao headDao = new HeadInstitutionLandsDao();
        HeadInstitutionLands headInstitutionLand = headDao.getHeadInstitutionLandByInstitutionAndLand(institutionId, landId);
        
        if(headInstitutionLand == null){
            DialogHelper.showMessage("Não foi possível encontrar a associação da horta com a instituição", this.plantingsScreen);
            return;
        }
        
        System.out.println(cropTypeId);
        
        CropTypes cropType = new CropTypesDao().getCropTypesById(cropTypeId);
        
        if(cropType == null){
            DialogHelper.showMessage("Tipo de plantio inválido!", this.plantingsScreen);
            return;
        }

        Plantings planting = new Plantings();
        planting.setId(null);
        planting.setHeadInstitutionLand(headInstitutionLand);
        planting.setCropType(cropType);
        planting.setApproximateHarvestDate(approximated);

        PlantingsDao dao = new PlantingsDao();
        String result = dao.save(planting);

        DialogHelper.showMessage(result, this.plantingsScreen);

        if(result.toLowerCase().contains("sucesso")){
            //this.plantingsScreen.clearInputs();
            this.loadPlantings(institutionId);
        }
    }
}
