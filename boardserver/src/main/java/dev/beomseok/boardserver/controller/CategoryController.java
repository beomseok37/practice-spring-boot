package dev.beomseok.boardserver.controller;

import dev.beomseok.boardserver.domain.Category;
import dev.beomseok.boardserver.dto.CategoryDTO;
import dev.beomseok.boardserver.dto.request.CategoryRequest;
import dev.beomseok.boardserver.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerCategory(@RequestBody CategoryRequest request) {
        CategoryDTO categoryDTO = new CategoryDTO(request.getName(), Category.SortStatus.CREATED);
        categoryService.register(categoryDTO);
    }

    @PatchMapping("/{categoryId}")
    public void updateCategories(@PathVariable("categoryId") Long categoryId,
                                 @Valid @RequestBody CategoryRequest categoryRequest){
        CategoryDTO categoryDTO = new CategoryDTO(categoryId, categoryRequest.getName(), Category.SortStatus.NEWEST,10,1);
        categoryService.update(categoryDTO);
    }

    @DeleteMapping("/{categoryId}")
    public void deleteCategories(@PathVariable("categoryId") Long categoryId){
        categoryService.delete(categoryId);
    }
}
