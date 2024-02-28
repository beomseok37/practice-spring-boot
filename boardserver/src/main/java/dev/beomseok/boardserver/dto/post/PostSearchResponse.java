package dev.beomseok.boardserver.dto.post;

import dev.beomseok.boardserver.domain.Post;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostSearchResponse {
    private String userId;
    private String title;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private int views;

    public PostSearchResponse(Post post) {
        this.userId = post.getUser().getUserId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.views = post.getViews();
    }
}
