package dev.beomseok.boardserver.dto.post;

import dev.beomseok.boardserver.domain.Comment;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CommentDTO {
    private String content;
    private List<CommentDTO> comments;

    private void setComments(List<CommentDTO> comments) {
        this.comments = comments;
    }

    public CommentDTO(String content) {
        this.content = content;
        this.comments = new ArrayList<>();
    }

    public CommentDTO(Comment comment) {
        this.content = comment.getContent();
        this.comments = convertToCommentDTO(comment.getChildComments());
    }

    public List<CommentDTO> convertToCommentDTO(List<Comment> comments) {
        if(comments.size() == 0 ) {
            return new ArrayList<>();
        }
        List<CommentDTO> newCommentsDTO = new ArrayList<>();

        comments.forEach(c -> {
            CommentDTO commentDTO = new CommentDTO(c.getContent());
            commentDTO.setComments(convertToCommentDTO(c.getChildComments()));
            newCommentsDTO.add(commentDTO);
        });

        return newCommentsDTO;
    }
}
