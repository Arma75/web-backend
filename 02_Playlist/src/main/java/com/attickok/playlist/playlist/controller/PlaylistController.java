package com.attickok.playlist.playlist.controller;

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

import com.attickok.playlist.common.dto.PageResponse;
import com.attickok.playlist.playlist.dto.PlaylistSaveRequest;
import com.attickok.playlist.playlist.dto.PlaylistResponse;
import com.attickok.playlist.playlist.dto.PlaylistSearchRequest;
import com.attickok.playlist.playlist.service.PlaylistService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {
    private final Logger logger = LoggerFactory.getLogger(PlaylistController.class);
    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody PlaylistSaveRequest playlistSaveRequest) {
        logger.info("[CREATE] Playlist create started. playlistSaveRequest: {}", playlistSaveRequest);

        playlistSaveRequest.validate(false);

        playlistService.create(playlistSaveRequest);

        logger.info("[CREATE] Playlist created successfully.");
        return ResponseEntity.status(201).body("Playlist created successfully.");
    }

    @PostMapping("/bulk")
    public ResponseEntity<Object> createBulk(@RequestBody List<PlaylistSaveRequest> playlistSaveRequests) {
        logger.info("[CREATE_BULK] Playlist create started. playlistSaveRequests: {}", playlistSaveRequests);
        for (PlaylistSaveRequest playlistSaveRequest : playlistSaveRequests) {
            playlistSaveRequest.validate(false);
        }

        playlistService.createBulk(playlistSaveRequests);
        
        logger.info("[CREATE_BULK] Playlist created successfully.");
        return ResponseEntity.status(201).body("Create success");
    }

    @PostMapping(value = "/excel-update", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadExcel(@RequestPart("file") MultipartFile file) {
        logger.info("[EXCEL-UPLOAD] Excel file upload started. Filename: {}", file.getOriginalFilename());
        playlistService.uploadExcel(file);

        logger.info("[EXCEL-UPLOAD] Excel file uploaded successfully.");
        return ResponseEntity.status(201).body("Create success");
    }
    

    @GetMapping("/{id}")
    public ResponseEntity<PlaylistResponse> findById(@PathVariable("id") Long id) {
        logger.info("[FIND_BY_ID] Playlist search started. id: {}", id);
        PlaylistResponse playlistResponse = playlistService.findById(id);

        logger.info("[FIND_BY_ID] Playlist search complete successfully.");
        return ResponseEntity.ok(playlistResponse);
    }

    @GetMapping
    public ResponseEntity<PageResponse<PlaylistResponse>> findAll(PlaylistSearchRequest playlistSearchRequest) {
        logger.info("[FIND_ALL] Playlist search started. playlistSearchRequest: {}", playlistSearchRequest);
        PageResponse<PlaylistResponse> pageResponse = playlistService.findAll(playlistSearchRequest);

        logger.info("[FIND_ALL] Playlist search complete successfully.");
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/excel-download")
    public void downloadExcel(PlaylistSearchRequest playlistSearchRequest, HttpServletResponse response) {
        logger.info("[EXCEL_DOWNLOAD] Excel file download started. playlistSearchRequest: {}", playlistSearchRequest);
        playlistService.downloadExcel(playlistSearchRequest, response);
        logger.info("[EXCEL_DOWNLOAD] Excel file downloaded successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody PlaylistSaveRequest playlistSaveRequest) {
        logger.info("[UPLOAD] Playlist upload started. id: {}, playlistSaveRequest: {}", id, playlistSaveRequest);

        playlistSaveRequest.validate(false);

        playlistService.update(id, playlistSaveRequest);

        logger.info("[UPLOAD] Playlist uploaded successfully.");
        return ResponseEntity.status(200).body("Update success");
    }

    @PutMapping("/bulk")
    public ResponseEntity<?> updateBulk(@RequestBody List<PlaylistSaveRequest> playlistSaveRequests) {
        logger.info("[UPLOAD_BULK] Playlist upload started. playlistSaveRequests: {}", playlistSaveRequests);
        for (PlaylistSaveRequest playlistSaveRequest : playlistSaveRequests) {
            playlistSaveRequest.validate(false);
        }
        
        playlistService.updateBulk(playlistSaveRequests);

        logger.info("[UPLOAD_BULK] Playlist uploaded successfully.");
        return ResponseEntity.status(200).body("Update success");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> patch(@PathVariable("id") Long id, @RequestBody PlaylistSaveRequest playlistSaveRequest) {
        logger.info("[PATCH] Playlist patch started. id: {}, playlistSaveRequest: {}", id, playlistSaveRequest);
        playlistSaveRequest.validate(true);
        
        playlistService.patch(id, playlistSaveRequest);
        
        logger.info("[PATCH] Playlist patched successfully.");
        return ResponseEntity.status(200).body("Patch success");
    }

    @PatchMapping("/bulk")
    public ResponseEntity<?> patchBulk(@RequestBody List<PlaylistSaveRequest> playlistSaveRequests) {
        logger.info("[PATCH_BULK] Playlist patch started. playlistSaveRequests: {}", playlistSaveRequests);
        for (PlaylistSaveRequest playlistSaveRequest : playlistSaveRequests) {
            playlistSaveRequest.validate(true);
        }

        playlistService.patchBulk(playlistSaveRequests);

        logger.info("[PATCH_BULK] Playlist patched successfully.");
        return ResponseEntity.status(200).body("Patch success");
    }

    @PatchMapping("/{id}/unuse")
    public ResponseEntity<?> unuse(@PathVariable("id") Long id) {
        logger.info("[UNUSE] Playlist unuse started. id: {}", id);

        playlistService.unuse(id);
        
        logger.info("[UNUSE] Playlist unused successfully.");
        return ResponseEntity.status(200).body("Unuse success");
    }

    @PatchMapping("/bulk/unuse")
    public ResponseEntity<?> unuseBulk(@RequestBody List<Long> ids) {
        logger.info("[UNUSE_BULK] Playlist unuse started. ids: {}", ids);
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("The 'ids' is required.");
        }

        playlistService.unuseBulk(ids);
        
        logger.info("[UNUSE_BULK] Playlist unused successfully.");
        return ResponseEntity.status(200).body("Unuse success");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        logger.info("[DELETE] Playlist delete started. id: {}", id);

        playlistService.delete(id);
        
        logger.info("[DELETE] Playlist deleted successfully.");
        return ResponseEntity.status(200).body("Delete success");
    }

    @DeleteMapping("/bulk")
    public ResponseEntity<?> deleteBulk(@RequestBody List<Long> ids) {
        logger.info("[DELETE_BULK] Playlist delete started. ids: {}", ids);
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("The 'ids' is required.");
        }

        playlistService.deleteBulk(ids);
        
        logger.info("[DELETE_BULK] Playlist deleted successfully.");
        return ResponseEntity.status(200).body("Delete success");
    }
}