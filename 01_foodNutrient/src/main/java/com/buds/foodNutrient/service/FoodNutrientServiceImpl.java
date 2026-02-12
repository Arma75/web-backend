package com.buds.foodNutrient.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.buds.foodNutrient.dao.FoodNutrientDAO;
import com.buds.foodNutrient.dto.FoodNutrientDTO;
import com.buds.foodNutrient.dto.PageResponse;

@Transactional(readOnly = true)
@Service
public class FoodNutrientServiceImpl implements FoodNutrientService {
    private final FoodNutrientDAO foodNutrientDAO;

    public FoodNutrientServiceImpl(FoodNutrientDAO foodNutrientDAO) {
        this.foodNutrientDAO = foodNutrientDAO;
    }

    @Transactional
    @Override
    public int create(FoodNutrientDTO foodNutrientDTO) {
        int createdCount = foodNutrientDAO.create(foodNutrientDTO);
        if (createdCount < 1) {
            throw new RuntimeException("Failed to create food nutrient record.");
        }

        return createdCount;
    }

    @Transactional
    @Override
    public int createBulk(List<FoodNutrientDTO> foodNutrientDTOs) {
        int createdCount = foodNutrientDAO.createBulk(foodNutrientDTOs);
        if (createdCount < 1) {
            throw new RuntimeException("Failed to create food nutrient record.");
        }

        return createdCount;
    }

    @Override
    public FoodNutrientDTO findById(Long id) {
        return foodNutrientDAO.findById(id);
    }

    @Override
    public PageResponse<FoodNutrientDTO> findAll(FoodNutrientDTO foodNutrientDTO) {
        List<FoodNutrientDTO> data = foodNutrientDAO.findAll(foodNutrientDTO);
        long totalCount = foodNutrientDAO.countAll(foodNutrientDTO);
        
        return new PageResponse<>(data, totalCount, foodNutrientDTO.getSize(), foodNutrientDTO.getPage());
    }

    @Transactional
    @Override
    public int update(Long id, FoodNutrientDTO foodNutrientDTO) {
        foodNutrientDTO.setId(id);

        int updatedCount = foodNutrientDAO.update(foodNutrientDTO);
        if (updatedCount < 1) {
            throw new RuntimeException("Failed to update food nutrient record.");
        }

        return updatedCount;
    }

    @Transactional
    @Override
    public int updateBulk(List<FoodNutrientDTO> foodNutrientDTOs) {
        int updatedCount = foodNutrientDAO.updateBulk(foodNutrientDTOs);
        if (updatedCount < 1) {
            throw new RuntimeException("Failed to update food nutrient record.");
        }

        return updatedCount;
    }

    @Transactional
    @Override
    public int patch(Long id, FoodNutrientDTO foodNutrientDTO) {
        foodNutrientDTO.setId(id);

        int patchedCount = foodNutrientDAO.patch(foodNutrientDTO);
        if (patchedCount < 1) {
            throw new RuntimeException("Failed to patch food nutrient record.");
        }
        return patchedCount;
    }

    @Transactional
    @Override
    public int patchBulk(List<FoodNutrientDTO> foodNutrientDTOs) {
        int patchedCount = foodNutrientDAO.patchBulk(foodNutrientDTOs);
        if (patchedCount < 1) {
            throw new RuntimeException("Failed to patch food nutrient record.");
        }
        return patchedCount;
    }

    @Transactional
    @Override
    public int unuse(Long id) {
        int patchedCount = foodNutrientDAO.unuse(id);
        if (patchedCount < 1) {
            throw new RuntimeException("Failed to unuse food nutrient record.");
        }
        return patchedCount;
    }

    @Transactional
    @Override
    public int unuseBulk(List<Long> ids) {
        int patchedCount = foodNutrientDAO.unuseBulk(ids);
        if (patchedCount < 1) {
            throw new RuntimeException("Failed to unuse food nutrient record.");
        }
        return patchedCount;
    }

    @Transactional
    @Override
    public int delete(Long id) {
        int deletedCount = foodNutrientDAO.delete(id);
        if (deletedCount < 1) {
            throw new RuntimeException("Failed to delete food nutrient record.");
        }
        return deletedCount;
    }

    @Transactional
    @Override
    public int deleteBulk(List<Long> ids) {
        int deletedCount = foodNutrientDAO.deleteBulk(ids);
        if (deletedCount < 1) {
            throw new RuntimeException("Failed to delete food nutrient record.");
        }
        return deletedCount;
    }
}