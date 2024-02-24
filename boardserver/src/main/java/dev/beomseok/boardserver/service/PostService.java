package dev.beomseok.boardserver.service;

import dev.beomseok.boardserver.dto.PostDTO;
import dev.beomseok.boardserver.dto.request.PostRequest;

import java.util.List;

public interface PostService {
    public void registerPost(String userId, PostRequest request);

    public List<PostDTO> getPosts(String userId);

    public void updatePost(String userId, Long id, PostRequest request);

    public void deletePost(String userId, long postId);
}
