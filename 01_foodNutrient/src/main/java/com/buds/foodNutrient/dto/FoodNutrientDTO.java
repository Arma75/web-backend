package com.buds.foodNutrient.dto;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

public class FoodNutrientDTO extends PageRequest {
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
    private LocalDateTime regDtmStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDtmEnd;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updDtm;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updDtmStart;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updDtmEnd;

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
    public LocalDateTime getRegDtmStart() {
        return regDtmStart;
    }
    public LocalDateTime getRegDtmEnd() {
        return regDtmEnd;
    }
    public LocalDateTime getUpdDtm() {
        return updDtm;
    }
    public LocalDateTime getUpdDtmStart() {
        return updDtmStart;
    }
    public LocalDateTime getUpdDtmEnd() {
        return updDtmEnd;
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
    public void setRegDtmStart(LocalDateTime regDtmStart) {
        this.regDtmStart = regDtmStart;
    }
    public void setRegDtmEnd(LocalDateTime regDtmEnd) {
        this.regDtmEnd = regDtmEnd;
    }
    public void setUpdDtm(LocalDateTime updDtm) {
        this.updDtm = updDtm;
    }
    public void setUpdDtmStart(LocalDateTime updDtmStart) {
        this.updDtmStart = updDtmStart;
    }
    public void setUpdDtmEnd(LocalDateTime updDtmEnd) {
        this.updDtmEnd = updDtmEnd;
    }

    @Override
    public String toString() {
        return "FoodNutrientDTO[" +
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
            "updDtm=" + updDtm + "," +
            "page=" + page + "," +
            "size=" + size + "," +
            "sort=" + sort + "]";
    }
}