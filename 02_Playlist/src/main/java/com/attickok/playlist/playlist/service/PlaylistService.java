package com.attickok.playlist.playlist.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.attickok.playlist.common.dto.PageResponse;
import com.attickok.playlist.playlist.dto.PlaylistSaveRequest;
import com.attickok.playlist.playlist.dto.PlaylistResponse;
import com.attickok.playlist.playlist.dto.PlaylistSearchRequest;

import jakarta.servlet.http.HttpServletResponse;

public interface PlaylistService {
    int create(PlaylistSaveRequest playlistSaveRequest);

    int createBulk(List<PlaylistSaveRequest> playlistSaveRequests);

    int uploadExcel(MultipartFile file);

    PlaylistResponse findById(Long id);

    PageResponse<PlaylistResponse> findAll(PlaylistSearchRequest playlistSearchRequest);

    void downloadExcel(PlaylistSearchRequest playlistSearchRequest, HttpServletResponse response);

    int update(Long id, PlaylistSaveRequest playlistSaveRequest);

    int updateBulk(List<PlaylistSaveRequest> playlistSaveRequests);

    int patch(Long id, PlaylistSaveRequest playlistSaveRequest);

    int patchBulk(List<PlaylistSaveRequest> playlistSaveRequests);

    int unuse(Long id);

    int unuseBulk(List<Long> ids);

    int delete(Long id);

    int deleteBulk(List<Long> ids);
}