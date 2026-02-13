package com.buds.project1.foodNutrient.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class FoodNutrientResponse {
    private Long id;
    private String company;
    private String food;
    private Integer serveSize;
    private String unit;
    private Long calorie;
    private Double carbohydrate;
    private Double protein;
    private Double fat;
    private String useYn;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDtm;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updDtm;

    public Long getId() {
        return id;
    }
    public String getCompany() {
        return company;
    }
    public String getFood() {
        return food;
    }
    public Integer getServeSize() {
        return serveSize;
    }
    public String getUnit() {
        return unit;
    }
    public Long getCalorie() {
        return calorie;
    }
    public Double getCarbohydrate() {
        return carbohydrate;
    }
    public Double getProtein() {
        return protein;
    }
    public Double getFat() {
        return fat;
    }
    public String getUseYn() {
        return useYn;
    }
    public LocalDateTime getRegDtm() {
        return regDtm;
    }
    public LocalDateTime getUpdDtm() {
        return updDtm;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public void setFood(String food) {
        this.food = food;
    }
    public void setServeSize(Integer serveSize) {
        this.serveSize = serveSize;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public void setCalorie(Long calorie) {
        this.calorie = calorie;
    }
    public void setCarbohydrate(Double carbohydrate) {
        this.carbohydrate = carbohydrate;
    }
    public void setProtein(Double protein) {
        this.protein = protein;
    }
    public void setFat(Double fat) {
        this.fat = fat;
    }
    public void setUseYn(String useYn) {
        this.useYn = useYn;
    }
    public void setRegDtm(LocalDateTime regDtm) {
        this.regDtm = regDtm;
    }
    public void setUpdDtm(LocalDateTime updDtm) {
        this.updDtm = updDtm;
    }

    @Override
    public String toString() {
        return "FoodNutrientResponse[" +
            "id=" + id + "," +
            "company=" + company + "," +
            "food=" + food + "," +
            "serveSize=" + serveSize + "," +
            "unit=" + unit + "," +
            "calorie=" + calorie + "," +
            "carbohydrate=" + carbohydrate + "," +
            "protein=" + protein + "," +
            "fat=" + fat + "," +
            "useYn=" + useYn + "," +
            "regDtm=" + regDtm + "," +
            "updDtm=" + updDtm + "]";
    }
}