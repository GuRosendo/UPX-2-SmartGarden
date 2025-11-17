/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.facens.upx2.smartgarden.model.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author Gustavo Rosendo Cardoso
 */
public class Plantings{
    private Long id;
    private HeadInstitutionLands headInstitutionLand;
    private CropTypes cropType;
    private Integer totalVolunteers;
    private Double plantingCosts;
    private Double plantingProfit;
    private Double plantingWeight;
    private Double finalProfit;
    private LocalDateTime approximateHarvestDate;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt; 
    private LocalDateTime updatedAt;
    
    public Plantings(){
    }

    public Plantings(Long id, HeadInstitutionLands headInstitutionLand, CropTypes cropType, Integer totalVolunteers, Double plantingCosts, Double plantingProfit, Double plantingWeight, Double finalProfit, LocalDateTime approximateHarvestDate, LocalDateTime createdAt, LocalDateTime deletedAt, LocalDateTime updatedAt){
        this.id = id;
        this.headInstitutionLand = headInstitutionLand;
        this.cropType = cropType;
        this.totalVolunteers = totalVolunteers;
        this.plantingCosts = plantingCosts;
        this.plantingProfit = plantingProfit;
        this.plantingWeight = plantingWeight;
        this.finalProfit = finalProfit;
        this.approximateHarvestDate = approximateHarvestDate;
        this.createdAt = createdAt;
        this.deletedAt = deletedAt;
        this.updatedAt = updatedAt;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public HeadInstitutionLands getHeadInstitutionLand(){
        return headInstitutionLand;
    }

    public void setHeadInstitutionLand(HeadInstitutionLands headInstitutionLand){
        this.headInstitutionLand = headInstitutionLand;
    }

    public CropTypes getCropType(){
        return cropType;
    }

    public void setCropType(CropTypes cropType){
        this.cropType = cropType;
    }

    public LocalDateTime getApproximateHarvestDate(){
        return approximateHarvestDate;
    }

    public void setApproximateHarvestDate(LocalDateTime approximateHarvestDate){
        this.approximateHarvestDate = approximateHarvestDate;
    }

    public LocalDateTime getCreatedAt(){
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt){
        this.createdAt = createdAt;
    }

    public LocalDateTime getDeletedAt(){
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt){
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getUpdatedAt(){
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt){
        this.updatedAt = updatedAt;
    }
    
    public Integer getTotalVolunteers() {
        return totalVolunteers;
    }

    public void setTotalVolunteers(Integer totalVolunteers) {
        this.totalVolunteers = totalVolunteers;
    }

    public Double getPlantingCosts() {
        return plantingCosts;
    }

    public void setPlantingCosts(Double plantingCosts) {
        this.plantingCosts = plantingCosts;
    }

    public Double getPlantingProfit() {
        return plantingProfit;
    }

    public void setPlantingProfit(Double plantingProfit) {
        this.plantingProfit = plantingProfit;
    }

    public Double getPlantingWeight() {
        return plantingWeight;
    }

    public void setPlantingWeight(Double plantingWeight) {
        this.plantingWeight = plantingWeight;
    }
    
    public Double getFinalProfit() {
        return this.plantingProfit - this.plantingCosts;
    }

    public void setFinalProfit(Double finalProfit) {
        this.finalProfit = finalProfit;
    }
    
    public String getCreatedAtFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.createdAt.format(formatter);
    }
    
    public String getCreatedAtHourFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        return this.createdAt.format(formatter);
    }
    
    public String getApproximateHarvestDateFormatted(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return this.approximateHarvestDate.format(formatter);
    }
}
