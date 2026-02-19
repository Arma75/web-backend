package com.attickok.playlist.playlist.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaylistSaveRequest {
    private Long id;
    private String genre;
    private String artist;
    private String songTitle;
    private String useYn;

    public void validate(boolean isPatch) {
        // 필수 값 체크
        if (!isPatch && (this.getSongTitle() == null || this.getSongTitle().isBlank())) {
            throw new IllegalArgumentException("The 'song title' is required.");
        }

        // 길이 체크
        if (this.getGenre() != null && this.getGenre().length() > 50) {
            throw new IllegalArgumentException("The 'genre' must be under 50 characters.");
        }
        if (this.getArtist() != null && this.getArtist().length() > 100) {
            throw new IllegalArgumentException("The 'artist' must be under 100 characters.");
        }
        if (this.getSongTitle() != null && this.getSongTitle().length() > 200) {
            throw new IllegalArgumentException("The 'song title' must be under 200 characters.");
        }

        // 포맷 체크
        if (this.getUseYn() != null && !this.getUseYn().equals("Y") && !this.getUseYn().equals("N")) {
            throw new IllegalArgumentException("The 'useYn' must be either 'Y' or 'N'.");
        }
    }
}