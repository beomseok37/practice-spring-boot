package dev.beomseok.boardserver.service.post;

import dev.beomseok.boardserver.dto.post.PostDTO;
import dev.beomseok.boardserver.dto.post.PostRequest;
import dev.beomseok.boardserver.dto.post.PostSearch;

import java.util.List;

public interface PostService {
    public void registerPost(String userId, PostRequest request);

    public List<PostDTO> getPosts(String userId);

    public List<PostDTO> getPosts(PostSearch postSearch);

    public void updatePost(String userId, Long id, PostRequest request);

    public void deletePost(String userId, long postId);
}
