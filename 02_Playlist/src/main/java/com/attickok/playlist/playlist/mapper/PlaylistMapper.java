package com.attickok.playlist.playlist.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.attickok.playlist.playlist.dto.PlaylistSaveRequest;
import com.attickok.playlist.playlist.dto.PlaylistResponse;
import com.attickok.playlist.playlist.dto.PlaylistSearchRequest;

@Mapper
public interface PlaylistMapper {
    int create(PlaylistSaveRequest playlistSaveRequest);

    int createBulk(List<PlaylistSaveRequest> playlistSaveRequests);

    PlaylistResponse findById(Long id);

    List<PlaylistResponse> findAll(PlaylistSearchRequest playlistSearchRequest);

    long countAll(PlaylistSearchRequest playlistSearchRequest);

    int update(PlaylistSaveRequest playlistSaveRequest);

    int updateBulk(List<PlaylistSaveRequest> playlistSaveRequests);

    int patch(PlaylistSaveRequest playlistSaveRequest);

    int patchBulk(List<PlaylistSaveRequest> playlistSaveRequests);

    int unuse(Long id);

    int unuseBulk(List<Long> ids);

    int delete(Long id);

    int deleteBulk(List<Long> ids);
}
