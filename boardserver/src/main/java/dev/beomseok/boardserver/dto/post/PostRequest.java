package dev.beomseok.boardserver.dto.post;

import dev.beomseok.boardserver.dto.TagDTO;
import lombok.Getter;

import java.util.List;

@Getter
public class PostRequest {
    private String title;
    private String content;
    private Boolean isAdmin;
    private long categoryId;
    private List<FileDTO> files;
    private List<TagDTO> tags;
}
