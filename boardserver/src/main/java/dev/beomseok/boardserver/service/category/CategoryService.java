package dev.beomseok.boardserver.service.category;

import dev.beomseok.boardserver.dto.category.CategoryDTO;

public interface CategoryService {
    void register(CategoryDTO categoryDTO);
    void update(CategoryDTO categoryDTO);
    void delete(long categoryId);
}
