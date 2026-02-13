package com.buds.project1.foodNutrient.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.buds.project1.common.dto.PageResponse;
import com.buds.project1.foodNutrient.dto.FoodNutrientRequest;
import com.buds.project1.foodNutrient.dto.FoodNutrientResponse;

import jakarta.servlet.http.HttpServletResponse;

public interface FoodNutrientService {
    int create(FoodNutrientRequest foodNutrientRequest);

    int createBulk(List<FoodNutrientRequest> foodNutrientRequests);

    int uploadExcel(MultipartFile file);

    FoodNutrientResponse findById(Long id);

    PageResponse<FoodNutrientResponse> findAll(FoodNutrientRequest foodNutrientRequest);

    void downloadExcel(FoodNutrientRequest foodNutrientRequest, HttpServletResponse response);

    int update(Long id, FoodNutrientRequest foodNutrientRequest);

    int updateBulk(List<FoodNutrientRequest> foodNutrientRequests);

    int patch(Long id, FoodNutrientRequest foodNutrientRequest);

    int patchBulk(List<FoodNutrientRequest> foodNutrientRequests);

    int unuse(Long id);

    int unuseBulk(List<Long> ids);

    int delete(Long id);

    int deleteBulk(List<Long> ids);
}