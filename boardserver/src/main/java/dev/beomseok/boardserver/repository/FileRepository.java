package dev.beomseok.boardserver.repository;

import dev.beomseok.boardserver.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File,Long> {
}
