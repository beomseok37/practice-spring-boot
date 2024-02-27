package dev.beomseok.boardserver.dto.post;

import dev.beomseok.boardserver.domain.Category;
import lombok.Getter;

@Getter
public class PostSearch {
    private Long id;
    private String title;
    private String content;
    private Long categoryId;
    private Long userId;
    private Category.SortStatus sortStatus;
}
