package dev.beomseok.boardserver.repository.post;

import dev.beomseok.boardserver.domain.Post;
import dev.beomseok.boardserver.dto.post.PostSearch;

import java.util.List;

public interface PostCustomRepository {
    List<Post> findPosts(String userId);

    Post findByPostId(Long postId);

    List<Post> findBySearch(PostSearch postSearch);
}
