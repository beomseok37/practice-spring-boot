package dev.beomseok.boardserver.dto.post;

import dev.beomseok.boardserver.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    private List<CommentDTO> comments;

    public PostDTO(Post post) {
        this.userId = post.getUser().getUserId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.createdDate = post.getCreatedDate();
        this.modifiedDate = post.getModifiedDate();
        this.views = post.getViews();
        this.files = post.getFiles().stream()
                .map(file -> new FileDTO(file.getName(), file.getPath(), file.getExtension()))
                .collect(Collectors.toList());

        this.comments = post.getComments().stream()
                .map(CommentDTO::new)
                .collect(Collectors.toList());
    }
}
