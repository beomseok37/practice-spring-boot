package dev.beomseok.boardserver.dto.post;

import lombok.Getter;

import java.util.List;

@Getter
public class PostRequest {
    private String title;
    private String content;
    private boolean isAdmin;
    private long categoryId;
    private List<FileDTO> files;
}
