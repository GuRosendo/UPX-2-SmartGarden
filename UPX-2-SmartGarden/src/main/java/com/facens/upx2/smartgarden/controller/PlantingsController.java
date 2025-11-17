/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.controller;

import com.facens.upx2.smartgarden.controller.helper.PlantingsControllerHelper;
import com.facens.upx2.smartgarden.view.form.PlantingsScreen;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class PlantingsController {
    private final PlantingsControllerHelper plantingsControllerHelper;
    private final PlantingsScreen plantingsScreen;
    
    public PlantingsController(PlantingsControllerHelper plantingsControllerHelper, PlantingsScreen plantingsScreen){
        this.plantingsControllerHelper = plantingsControllerHelper;
        this.plantingsScreen = plantingsScreen;
    }
    
    public void loadPlantings(Long institutionId){
    
    }
    
    public void loadPlantingsWithSearch(Long institutionId, String search){
    
    }
    
    public void loadLands(Long institutionId){
        
    }
    
    public void registerPlanting(String approximatedHarvestDate, Long landId, Long institutionId){
        
    }
}
