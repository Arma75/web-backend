package com.attickok.playlist.playlist.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.attickok.playlist.common.dto.PageResponse;
import com.attickok.playlist.common.utils.ExcelUtil;
import com.attickok.playlist.playlist.dto.PlaylistSaveRequest;
import com.attickok.playlist.playlist.dto.PlaylistResponse;
import com.attickok.playlist.playlist.dto.PlaylistSearchRequest;
import com.attickok.playlist.playlist.mapper.PlaylistMapper;

import jakarta.servlet.http.HttpServletResponse;

@Transactional(readOnly = true)
@Service
public class PlaylistServiceImpl implements PlaylistService {
    private final PlaylistMapper playlistMapper;

    public PlaylistServiceImpl(PlaylistMapper playlistMapper) {
        this.playlistMapper = playlistMapper;
    }

    @Transactional
    @Override
    public int create(PlaylistSaveRequest playlistSaveRequest) {
        int createdCount = playlistMapper.create(playlistSaveRequest);
        if (createdCount < 1) {
            throw new RuntimeException("Failed to create playlist record.");
        }

        return createdCount;
    }

    @Transactional
    @Override
    public int createBulk(List<PlaylistSaveRequest> playlistSaveRequests) {
        int createdCount = playlistMapper.createBulk(playlistSaveRequests);
        if (createdCount < 1) {
            throw new RuntimeException("Failed to create playlist record.");
        }

        return createdCount;
    }

    @Transactional
    @Override
    public int uploadExcel(MultipartFile file) {
        List<PlaylistSaveRequest> playlistSaveRequests = ExcelUtil.convertToList(file, 1, PlaylistSaveRequest.class);
        for (PlaylistSaveRequest playlistSaveRequest : playlistSaveRequests) {
            playlistSaveRequest.validate(false);
        }

        return createBulk(playlistSaveRequests);
    }

    @Override
    public PlaylistResponse findById(Long id) {
        return playlistMapper.findById(id);
    }

    @Override
    public PageResponse<PlaylistResponse> findAll(PlaylistSearchRequest playlistSearchRequest) {
        String sort = playlistSearchRequest.getSort();
        StringBuilder useSort = new StringBuilder();
        if (sort != null && !sort.isBlank()) {
            String[] orders = sort.split(";");
            for (int i = 0; i < orders.length; i++) {
                String[] tokens = orders[i].split(",");
                if (tokens.length < 2) {
                    continue;
                }
                String column = tokens[0];
                String direction = tokens[1];

                List<String> allowedColumns = List.of("ID", "GENRE", "ARTIST", "SONG_TITLE", "USE_YN", "REG_DTM", "UPD_DTM");
                if (!allowedColumns.contains(column.toUpperCase())) {
                    throw new IllegalArgumentException("Invalid sort parameter.");
                }
                if (!"ASC".equalsIgnoreCase(direction) && !"DESC".equalsIgnoreCase(direction)) {
                    throw new IllegalArgumentException("Invalid sort parameter.");
                }

                if (i > 0) {
                    useSort.append(", ");
                }
                useSort.append(column + " " + direction);
            }
        } else {
            useSort.append("ID DESC");
        }

        playlistSearchRequest.setSort(useSort.toString());

        List<PlaylistResponse> data = playlistMapper.findAll(playlistSearchRequest);
        long totalCount = playlistMapper.countAll(playlistSearchRequest);
        
        return new PageResponse<>(data, totalCount, playlistSearchRequest.getSize(), playlistSearchRequest.getPage());
    }

    @Override
    public void downloadExcel(PlaylistSearchRequest playlistSearchRequest, HttpServletResponse response) {
        PageResponse<PlaylistResponse> pageResponse = findAll(playlistSearchRequest);
        List<PlaylistResponse> playlistResponses = pageResponse.getData();
        String[] headers = {"아이디", "장르", "아티스트", "제목", "사용여부", "등록일시", "수정일시"};
        
        ExcelUtil.download(playlistResponses, "Playlist", headers, response);
    }

    @Transactional
    @Override
    public int update(Long id, PlaylistSaveRequest playlistSaveRequest) {
        playlistSaveRequest.setId(id);

        int updatedCount = playlistMapper.update(playlistSaveRequest);
        if (updatedCount < 1) {
            throw new RuntimeException("Failed to update playlist record.");
        }

        return updatedCount;
    }

    @Transactional
    @Override
    public int updateBulk(List<PlaylistSaveRequest> playlistSaveRequests) {
        int updatedCount = playlistMapper.updateBulk(playlistSaveRequests);
        if (updatedCount < 1) {
            throw new RuntimeException("Failed to update playlist record.");
        }

        return updatedCount;
    }

    @Transactional
    @Override
    public int patch(Long id, PlaylistSaveRequest playlistSaveRequest) {
        playlistSaveRequest.setId(id);

        int patchedCount = playlistMapper.patch(playlistSaveRequest);
        if (patchedCount < 1) {
            throw new RuntimeException("Failed to patch playlist record.");
        }
        return patchedCount;
    }

    @Transactional
    @Override
    public int patchBulk(List<PlaylistSaveRequest> playlistSaveRequests) {
        int patchedCount = playlistMapper.patchBulk(playlistSaveRequests);
        if (patchedCount < 1) {
            throw new RuntimeException("Failed to patch playlist record.");
        }
        return patchedCount;
    }

    @Transactional
    @Override
    public int unuse(Long id) {
        int patchedCount = playlistMapper.unuse(id);
        if (patchedCount < 1) {
            throw new RuntimeException("Failed to unuse playlist record.");
        }
        return patchedCount;
    }

    @Transactional
    @Override
    public int unuseBulk(List<Long> ids) {
        int patchedCount = playlistMapper.unuseBulk(ids);
        if (patchedCount < 1) {
            throw new RuntimeException("Failed to unuse playlist record.");
        }
        return patchedCount;
    }

    @Transactional
    @Override
    public int delete(Long id) {
        int deletedCount = playlistMapper.delete(id);
        if (deletedCount < 1) {
            throw new RuntimeException("Failed to delete playlist record.");
        }
        return deletedCount;
    }

    @Transactional
    @Override
    public int deleteBulk(List<Long> ids) {
        int deletedCount = playlistMapper.deleteBulk(ids);
        if (deletedCount < 1) {
            throw new RuntimeException("Failed to delete playlist record.");
        }
        return deletedCount;
    }
}