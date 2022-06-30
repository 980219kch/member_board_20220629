package com.its.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardTitle;
    private String boardWriter;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedDate;
    private MultipartFile boardFile;
    private String boardFileName;

    public BoardDTO(Long id, String boardTitle, String boardWriter, int boardHits, LocalDateTime boardCreatedDate) {
        this.id = id;
        this.boardTitle = boardTitle;
        this.boardWriter = boardWriter;
        this.boardHits = boardHits;
        this.boardCreatedDate = boardCreatedDate;
    }
}
