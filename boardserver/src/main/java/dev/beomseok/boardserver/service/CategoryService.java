package dev.beomseok.boardserver.service;

import dev.beomseok.boardserver.dto.CategoryDTO;

public interface CategoryService {
    void register(CategoryDTO categoryDTO);
    void update(CategoryDTO categoryDTO);
    void delete(long categoryId);
}
