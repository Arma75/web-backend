package com.buds.foodNutrient.controller;

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

import com.buds.foodNutrient.dto.FoodNutrientDTO;
import com.buds.foodNutrient.dto.PageResponse;
import com.buds.foodNutrient.service.FoodNutrientService;

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
    public ResponseEntity<Object> create(@RequestBody FoodNutrientDTO foodNutrientDTO) {
        logger.info("foodNutrientDTO::{}", foodNutrientDTO);

        validate(foodNutrientDTO, false);

        foodNutrientService.create(foodNutrientDTO);

        return ResponseEntity.status(201).body("Create success");
    }

    @PostMapping("/bulk")
    public ResponseEntity<Object> createBulk(@RequestBody List<FoodNutrientDTO> foodNutrientDTOs) {
        logger.info("foodNutrientDTOs::{}", foodNutrientDTOs);
        for (FoodNutrientDTO foodNutrientDTO : foodNutrientDTOs) {
            validate(foodNutrientDTO, false);
        }

        foodNutrientService.createBulk(foodNutrientDTOs);
        
        return ResponseEntity.status(201).body("Create success");
    }

    @PostMapping(value = "/excel-update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadExcel(@RequestPart("file") MultipartFile file) {
        // todo: not yet
        return ResponseEntity.status(201).body("Create success");
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<FoodNutrientDTO> findById(@PathVariable("id") Long id) {
        logger.info("id::{}", id);
        return ResponseEntity.ok(foodNutrientService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<FoodNutrientDTO>> findAll(FoodNutrientDTO foodNutrientDTO) {
        logger.info("foodNutrientDTO::{}", foodNutrientDTO);
        return ResponseEntity.ok(foodNutrientService.findAll(foodNutrientDTO));
    }

    @GetMapping("/excel-download")
    public ResponseEntity<?> downloadExcel(FoodNutrientDTO foodNutrientDTO, HttpServletResponse response) {
        logger.info("foodNutrientDTO::{}", foodNutrientDTO);
        
        return ResponseEntity.ok("Read success");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody FoodNutrientDTO foodNutrientDTO) {
        logger.info("id::{}, foodNutrientDTO::{}", id, foodNutrientDTO);

        validate(foodNutrientDTO, false);

        foodNutrientService.update(id, foodNutrientDTO);

        return ResponseEntity.status(200).body("Update success");
    }

    @PutMapping("/bulk")
    public ResponseEntity<?> updateBulk(@RequestBody List<FoodNutrientDTO> foodNutrientDTOs) {
        logger.info("foodNutrientDTOs::{}", foodNutrientDTOs);
        for (FoodNutrientDTO foodNutrientDTO : foodNutrientDTOs) {
            validate(foodNutrientDTO, false);
        }
        
        foodNutrientService.updateBulk(foodNutrientDTOs);

        return ResponseEntity.status(200).body("Update success");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable("id") Long id, @RequestBody FoodNutrientDTO foodNutrientDTO) {
        logger.info("id::{}, foodNutrientDTO::{}", id, foodNutrientDTO);
        validate(foodNutrientDTO, true);
        
        foodNutrientService.patch(id, foodNutrientDTO);
        
        return ResponseEntity.status(200).body("Patch success");
    }

    @PatchMapping("/bulk")
    public ResponseEntity<?> patchBulk(@RequestBody List<FoodNutrientDTO> foodNutrientDTOs) {
        logger.info("foodNutrientDTOs::{}", foodNutrientDTOs);
        for (FoodNutrientDTO foodNutrientDTO : foodNutrientDTOs) {
            validate(foodNutrientDTO, true);
        }

        foodNutrientService.patchBulk(foodNutrientDTOs);

        return ResponseEntity.status(200).body("Patch success");
    }

    @PatchMapping("/{id}/unuse")
    public ResponseEntity<?> unuse(@PathVariable("id") Long id) {
        logger.info("id::{}", id);

        foodNutrientService.unuse(id);
        
        return ResponseEntity.status(200).body("Unuse success");
    }

    @PatchMapping("/bulk/unuse")
    public ResponseEntity<?> unuseBulk(@RequestBody List<Long> ids) {
        logger.info("ids::{}", ids);

        foodNutrientService.unuseBulk(ids);
        
        return ResponseEntity.status(200).body("Unuse success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        logger.info("id::{}", id);

        foodNutrientService.delete(id);
        
        return ResponseEntity.status(200).body("Delete success");
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<?> deleteBulk(@RequestBody List<Long> ids) {
        logger.info("ids::{}", ids);

        foodNutrientService.deleteBulk(ids);
        
        return ResponseEntity.status(200).body("Delete success");
    }

    private void validate(FoodNutrientDTO foodNutrientDTO, boolean isPatch) {
        // 필수 값 체크
        if (!isPatch && (foodNutrientDTO.getFood() == null || foodNutrientDTO.getFood().isBlank())) {
            throw new IllegalArgumentException("The 'food' is required.");
        }
        if (isPatch && (foodNutrientDTO.getFood() != null && foodNutrientDTO.getFood().isBlank())) {
            throw new IllegalArgumentException("The 'food' is required.");
        }

        // 길이 체크
        if (foodNutrientDTO.getCompany() != null && foodNutrientDTO.getCompany().length() > 100) {
            throw new IllegalArgumentException("The 'company' must be under 100 characters.");
        }
        if (foodNutrientDTO.getFood() != null && foodNutrientDTO.getFood().length() > 100) {
            throw new IllegalArgumentException("The 'food' must be under 100 characters.");
        }
        if (foodNutrientDTO.getUnit() != null && foodNutrientDTO.getUnit().length() > 10) {
            throw new IllegalArgumentException("The 'unit' must be under 10 characters.");
        }
    }
}