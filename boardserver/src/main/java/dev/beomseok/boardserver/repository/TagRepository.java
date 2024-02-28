package dev.beomseok.boardserver.repository;

import dev.beomseok.boardserver.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Long> {
}
