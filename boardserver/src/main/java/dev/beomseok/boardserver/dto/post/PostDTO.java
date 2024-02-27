package dev.beomseok.boardserver.dto.post;

import com.querydsl.core.annotations.QueryProjection;
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

    public static List<PostDTO> postToDTO(List<Post> posts) {
        return posts.stream().map(post -> {
            List<FileDTO> files = post.getFiles().stream()
                    .map(file -> new FileDTO(file.getName(), file.getPath(), file.getExtension()))
                    .collect(Collectors.toList());

            return new PostDTO(post.getUser().getUserId(), post.getTitle(), post.getContent(),
                    post.getCreatedDate(), post.getModifiedDate(), post.getViews(), files);
        }).collect(Collectors.toList());
    }
}
