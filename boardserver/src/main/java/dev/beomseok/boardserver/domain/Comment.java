package dev.beomseok.boardserver.domain;

import dev.beomseok.boardserver.dto.comment.CommentRequest;
import dev.beomseok.boardserver.repository.CommentRepository;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<Comment> childComments = new ArrayList<>();

    public Comment(String content, Post post, Comment parentComment) {
        this.content = content;
        setPost(post);

        if(parentComment!=null){
            addComment(parentComment);
        }
    }

    private void setPost(Post post){
        this.post = post;
        post.getComments().add(this);
    }

    private void addComment(Comment parentComment){
        this.parentComment = parentComment;
        parentComment.getChildComments().add(this);
    }

    public void updateComment(String content){
        this.content = content;
    }
}
