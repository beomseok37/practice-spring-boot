package dev.beomseok.boardserver.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
public class PostDTO {
    private String userId;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int views;
    private List<FileDTO> files;

    @QueryProjection
    public PostDTO(String userId, String title, String content, LocalDateTime createdDate, LocalDateTime modifiedDate, int views, List<FileDTO> files) {
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
        this.views = views;
        this.files = files;
    }
}
