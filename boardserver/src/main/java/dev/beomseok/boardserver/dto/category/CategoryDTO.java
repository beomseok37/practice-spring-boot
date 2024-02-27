package dev.beomseok.boardserver.dto.category;

import lombok.Getter;
import lombok.NoArgsConstructor;

import static dev.beomseok.boardserver.domain.Category.SortStatus;

@Getter
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private SortStatus status;
    private int size;
    private int offset;

    public CategoryDTO(String name, SortStatus status) {
        this.name = name;
        this.status = status;
    }

    public CategoryDTO(Long id, String name, SortStatus status, int size, int offset) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.size = size;
        this.offset = offset;
    }
}
