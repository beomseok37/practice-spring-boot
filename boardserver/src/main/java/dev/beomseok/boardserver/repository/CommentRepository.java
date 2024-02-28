package dev.beomseok.boardserver.repository;

import dev.beomseok.boardserver.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment,Long> {
}
