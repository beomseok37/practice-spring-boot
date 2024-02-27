package dev.beomseok.boardserver.service;

import dev.beomseok.boardserver.domain.Category;
import dev.beomseok.boardserver.dto.category.CategoryDTO;
import dev.beomseok.boardserver.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public void register(CategoryDTO categoryDTO) {
        Category category = Category.createCategory(categoryDTO);
        categoryRepository.save(category);
    }

    @Override
    public void update(CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(categoryDTO.getId())
                .orElseThrow(()->new IllegalArgumentException("해당 ID와 일치하는 카테고리가 없습니다."));

        category.update(categoryDTO);
    }

    @Override
    public void delete(long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()->new IllegalArgumentException("해당 ID와 일치하는 카테고리가 없습니다."));

        categoryRepository.delete(category);
    }
}
