package com.buds.project1.foodNutrient.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.buds.project1.common.dto.PageResponse;
import com.buds.project1.foodNutrient.dto.FoodNutrientRequest;
import com.buds.project1.foodNutrient.dto.FoodNutrientResponse;
import com.buds.project1.foodNutrient.service.FoodNutrientService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/food-nutrient")
public class FoodNutrientController {
    private final Logger logger = LoggerFactory.getLogger(FoodNutrientController.class);
    private final FoodNutrientService foodNutrientService;

    public FoodNutrientController(FoodNutrientService foodNutrientService) {
        this.foodNutrientService = foodNutrientService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody FoodNutrientRequest foodNutrientRequest) {
        logger.info("[CREATE] FoodNutrient create started. foodNutrientRequest: {}", foodNutrientRequest);

        foodNutrientRequest.validate(false);

        foodNutrientService.create(foodNutrientRequest);

        logger.info("[CREATE] FoodNutrient created successfully.");
        return ResponseEntity.status(201).body("FoodNutrient created successfully.");
    }

    @PostMapping("/bulk")
    public ResponseEntity<Object> createBulk(@RequestBody List<FoodNutrientRequest> foodNutrientRequests) {
        logger.info("[CREATE_BULK] FoodNutrient create started. foodNutrientRequests: {}", foodNutrientRequests);
        for (FoodNutrientRequest foodNutrientRequest : foodNutrientRequests) {
            foodNutrientRequest.validate(false);
        }

        foodNutrientService.createBulk(foodNutrientRequests);
        
        logger.info("[CREATE_BULK] FoodNutrient created successfully.");
        return ResponseEntity.status(201).body("Create success");
    }

    @PostMapping(value = "/excel-update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadExcel(@RequestPart("file") MultipartFile file) {
        logger.info("[EXCEL-UPLOAD] Excel file upload started. Filename: {}", file.getOriginalFilename());
        foodNutrientService.uploadExcel(file);

        logger.info("[EXCEL-UPLOAD] Excel file uploaded successfully.");
        return ResponseEntity.status(201).body("Create success");
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<FoodNutrientResponse> findById(@PathVariable("id") Long id) {
        logger.info("[FIND_BY_ID] FoodNutrient search started. id: {}", id);
        FoodNutrientResponse foodNutrientResponse = foodNutrientService.findById(id);

        logger.info("[FIND_BY_ID] FoodNutrient search complete successfully.");
        return ResponseEntity.ok(foodNutrientResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse<FoodNutrientResponse>> findAll(FoodNutrientRequest foodNutrientRequest) {
        logger.info("[FIND_ALL] FoodNutrient search started. foodNutrientRequest: {}", foodNutrientRequest);
        PageResponse<FoodNutrientResponse> pageResponse = foodNutrientService.findAll(foodNutrientRequest);

        logger.info("[FIND_ALL] FoodNutrient search complete successfully.");
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/excel-download")
    public void downloadExcel(FoodNutrientRequest foodNutrientRequest, HttpServletResponse response) {
        logger.info("[EXCEL_DOWNLOAD] Excel file download started. foodNutrientRequest: {}", foodNutrientRequest);
        foodNutrientService.downloadExcel(foodNutrientRequest, response);
        logger.info("[EXCEL_DOWNLOAD] Excel file downloaded successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody FoodNutrientRequest foodNutrientRequest) {
        logger.info("[UPLOAD] FoodNutrient upload started. id: {}, foodNutrientRequest: {}", id, foodNutrientRequest);

        foodNutrientRequest.validate(false);

        foodNutrientService.update(id, foodNutrientRequest);

        logger.info("[UPLOAD] FoodNutrient uploaded successfully.");
        return ResponseEntity.status(200).body("Update success");
    }

    @PutMapping("/bulk")
    public ResponseEntity<?> updateBulk(@RequestBody List<FoodNutrientRequest> foodNutrientRequests) {
        logger.info("[UPLOAD_BULK] FoodNutrient upload started. foodNutrientRequests: {}", foodNutrientRequests);
        for (FoodNutrientRequest foodNutrientRequest : foodNutrientRequests) {
            foodNutrientRequest.validate(false);
        }
        
        foodNutrientService.updateBulk(foodNutrientRequests);

        logger.info("[UPLOAD_BULK] FoodNutrient uploaded successfully.");
        return ResponseEntity.status(200).body("Update success");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable("id") Long id, @RequestBody FoodNutrientRequest foodNutrientRequest) {
        logger.info("[PATCH] FoodNutrient patch started. id: {}, foodNutrientRequest: {}", id, foodNutrientRequest);
        foodNutrientRequest.validate(true);
        
        foodNutrientService.patch(id, foodNutrientRequest);
        
        logger.info("[PATCH] FoodNutrient patched successfully.");
        return ResponseEntity.status(200).body("Patch success");
    }

    @PatchMapping("/bulk")
    public ResponseEntity<?> patchBulk(@RequestBody List<FoodNutrientRequest> foodNutrientRequests) {
        logger.info("[PATCH_BULK] FoodNutrient patch started. foodNutrientRequests: {}", foodNutrientRequests);
        for (FoodNutrientRequest foodNutrientRequest : foodNutrientRequests) {
            foodNutrientRequest.validate(true);
        }

        foodNutrientService.patchBulk(foodNutrientRequests);

        logger.info("[PATCH_BULK] FoodNutrient patched successfully.");
        return ResponseEntity.status(200).body("Patch success");
    }

    @PatchMapping("/{id}/unuse")
    public ResponseEntity<?> unuse(@PathVariable("id") Long id) {
        logger.info("[UNUSE] FoodNutrient unuse started. id: {}", id);

        foodNutrientService.unuse(id);
        
        logger.info("[UNUSE] FoodNutrient unused successfully.");
        return ResponseEntity.status(200).body("Unuse success");
    }

    @PatchMapping("/bulk/unuse")
    public ResponseEntity<?> unuseBulk(@RequestBody List<Long> ids) {
        logger.info("[UNUSE_BULK] FoodNutrient unuse started. ids: {}", ids);

        foodNutrientService.unuseBulk(ids);
        
        logger.info("[UNUSE_BULK] FoodNutrient unused successfully.");
        return ResponseEntity.status(200).body("Unuse success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        logger.info("[DELETE] FoodNutrient delete started. id: {}", id);

        foodNutrientService.delete(id);
        
        logger.info("[DELETE] FoodNutrient deleted successfully.");
        return ResponseEntity.status(200).body("Delete success");
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<?> deleteBulk(@RequestBody List<Long> ids) {
        logger.info("[DELETE_BULK] FoodNutrient delete started. ids: {}", ids);

        foodNutrientService.deleteBulk(ids);
        
        logger.info("[DELETE_BULK] FoodNutrient deleted successfully.");
        return ResponseEntity.status(200).body("Delete success");
    }
}