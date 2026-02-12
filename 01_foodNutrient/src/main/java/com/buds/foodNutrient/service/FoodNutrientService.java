package com.buds.foodNutrient.service;

import java.util.List;


import com.buds.foodNutrient.dto.FoodNutrientDTO;
import com.buds.foodNutrient.dto.PageResponse;

public interface FoodNutrientService {
    int create(FoodNutrientDTO foodNutrientDTO);

    int createBulk(List<FoodNutrientDTO> foodNutrientDTOs);

    FoodNutrientDTO findById(Long id);

    PageResponse<FoodNutrientDTO> findAll(FoodNutrientDTO foodNutrientDTO);

    int update(Long id, FoodNutrientDTO foodNutrientDTO);

    int updateBulk(List<FoodNutrientDTO> foodNutrientDTOs);

    int patch(Long id, FoodNutrientDTO foodNutrientDTO);

    int patchBulk(List<FoodNutrientDTO> foodNutrientDTOs);

    int unuse(Long id);

    int unuseBulk(List<Long> ids);

    int delete(Long id);

    int deleteBulk(List<Long> ids);
}