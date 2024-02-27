package dev.beomseok.boardserver.dto.category;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CategoryRequest {
    @NotBlank(message = "카테고리 name이 비어 있습니다.")
    private String name;
}
