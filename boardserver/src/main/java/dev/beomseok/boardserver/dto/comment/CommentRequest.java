package dev.beomseok.boardserver.dto.comment;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentRequest {
    @NotBlank(message = "post id가 비어있습니다.")
    private Long postId;

    @NotBlank(message = "comment content는 비어있으면 안됩니다.")
    private String content;
    private Long parentId;
}
