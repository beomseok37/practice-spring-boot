package dev.beomseok.boardserver.service.post;

import dev.beomseok.boardserver.dto.comment.CommentRequest;
import dev.beomseok.boardserver.dto.post.PostDTO;
import dev.beomseok.boardserver.dto.post.PostRequest;
import dev.beomseok.boardserver.dto.post.PostSearch;
import dev.beomseok.boardserver.dto.post.PostSearchResponse;

import java.util.List;

public interface PostService {
    public void registerPost(String userId, PostRequest request);

    public List<PostDTO> getPosts(String userId);

    public List<PostSearchResponse> getPosts(PostSearch postSearch);

    public List<PostSearchResponse> getPosts(Long tagId);

    public void updatePost(String userId, Long id, PostRequest request);

    public void deletePost(String userId, long postId);

    void registerComment(CommentRequest request);

    void updateComment(Long commentId, CommentRequest request);

    void deleteComment(Long commentId);
}
