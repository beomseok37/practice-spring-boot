package dev.beomseok.boardserver.repository;

import dev.beomseok.boardserver.domain.Post;

import java.util.List;

public interface PostCustomRepository {
    List<Post> findPosts(String userId);

    Post findByPostId(Long postId);
}
