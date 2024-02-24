package dev.beomseok.boardserver.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CategoryRequest {
    @NotBlank(message = "카테고리 name이 비어 있습니다.")
    private String name;
}
