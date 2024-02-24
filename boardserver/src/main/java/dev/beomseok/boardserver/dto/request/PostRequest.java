package dev.beomseok.boardserver.dto.request;

import lombok.Getter;

@Getter
public class PostRequest {
    private String title;
    private String content;
    private boolean isAdmin;
    private long categoryId;
}
