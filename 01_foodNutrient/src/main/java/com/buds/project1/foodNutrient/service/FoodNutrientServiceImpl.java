package com.buds.project1.foodNutrient.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.buds.project1.common.dto.PageResponse;
import com.buds.project1.common.utils.ExcelUtil;
import com.buds.project1.foodNutrient.dao.FoodNutrientDAO;
import com.buds.project1.foodNutrient.dto.FoodNutrientRequest;
import com.buds.project1.foodNutrient.dto.FoodNutrientResponse;

import jakarta.servlet.http.HttpServletResponse;

@Transactional(readOnly = true)
@Service
public class FoodNutrientServiceImpl implements FoodNutrientService {
    private final FoodNutrientDAO foodNutrientDAO;

    public FoodNutrientServiceImpl(FoodNutrientDAO foodNutrientDAO) {
        this.foodNutrientDAO = foodNutrientDAO;
    }

    @Transactional
    @Override
    public int create(FoodNutrientRequest foodNutrientRequest) {
        int createdCount = foodNutrientDAO.create(foodNutrientRequest);
        if (createdCount < 1) {
            throw new RuntimeException("Failed to create food nutrient record.");
        }

        return createdCount;
    }

    @Transactional
    @Override
    public int createBulk(List<FoodNutrientRequest> foodNutrientRequests) {
        int createdCount = foodNutrientDAO.createBulk(foodNutrientRequests);
        if (createdCount < 1) {
            throw new RuntimeException("Failed to create food nutrient record.");
        }

        return createdCount;
    }

    @Transactional
    @Override
    public int uploadExcel(MultipartFile file) {
        List<FoodNutrientRequest> foodNutrientRequests = ExcelUtil.convertToList(file, 1, FoodNutrientRequest.class);
        System.out.println("foodNutrientRequests::" + foodNutrientRequests);
        for (FoodNutrientRequest foodNutrientRequest : foodNutrientRequests) {
            foodNutrientRequest.validate(false);
        }

        return createBulk(foodNutrientRequests);
    }

    @Override
    public FoodNutrientResponse findById(Long id) {
        return foodNutrientDAO.findById(id);
    }

    @Override
    public PageResponse<FoodNutrientResponse> findAll(FoodNutrientRequest foodNutrientRequest) {
        List<FoodNutrientResponse> data = foodNutrientDAO.findAll(foodNutrientRequest);
        long totalCount = foodNutrientDAO.countAll(foodNutrientRequest);
        
        return new PageResponse<>(data, totalCount, foodNutrientRequest.getSize(), foodNutrientRequest.getPage());
    }

    @Override
    public void downloadExcel(FoodNutrientRequest foodNutrientRequest, HttpServletResponse response) {
        PageResponse<FoodNutrientResponse> pageResponse = findAll(foodNutrientRequest);
        List<FoodNutrientResponse> foodNutrientResponses = pageResponse.getData();
        String[] headers = {"아이디", "제조사", "식품명", "1회 제공량", "단위", "칼로리", "탄수화물", "단백질", "지방", "사용여부", "등록일시", "수정일시"};
        
        ExcelUtil.download(foodNutrientResponses, "FoodNutrient", headers, response);
    }

    @Transactional
    @Override
    public int update(Long id, FoodNutrientRequest foodNutrientRequest) {
        foodNutrientRequest.setId(id);

        int updatedCount = foodNutrientDAO.update(foodNutrientRequest);
        if (updatedCount < 1) {
            throw new RuntimeException("Failed to update food nutrient record.");
        }

        return updatedCount;
    }

    @Transactional
    @Override
    public int updateBulk(List<FoodNutrientRequest> foodNutrientRequests) {
        int updatedCount = foodNutrientDAO.updateBulk(foodNutrientRequests);
        if (updatedCount < 1) {
            throw new RuntimeException("Failed to update food nutrient record.");
        }

        return updatedCount;
    }

    @Transactional
    @Override
    public int patch(Long id, FoodNutrientRequest foodNutrientRequest) {
        foodNutrientRequest.setId(id);

        int patchedCount = foodNutrientDAO.patch(foodNutrientRequest);
        if (patchedCount < 1) {
            throw new RuntimeException("Failed to patch food nutrient record.");
        }
        return patchedCount;
    }

    @Transactional
    @Override
    public int patchBulk(List<FoodNutrientRequest> foodNutrientRequests) {
        int patchedCount = foodNutrientDAO.patchBulk(foodNutrientRequests);
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