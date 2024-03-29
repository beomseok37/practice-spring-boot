package dev.beomseok.boardserver.repository.post;

import dev.beomseok.boardserver.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>, PostCustomRepository {
}
