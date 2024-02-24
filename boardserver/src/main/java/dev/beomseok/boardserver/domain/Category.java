package dev.beomseok.boardserver.domain;

import dev.beomseok.boardserver.dto.CategoryDTO;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
@Setter
public class Category extends BaseEntity{
    public static enum SortStatus {
        CREATED, NEWEST, OLDEST
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String name;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(10)")
    SortStatus status;

    //==생성자 메서드==//
    public static Category createCategory(CategoryDTO categoryDTO){
        Category category = new Category();
        category.setName(categoryDTO.getName());
        category.setStatus(categoryDTO.getStatus());
        return category;
    }

    //==수정 메서드==//
    public void update(CategoryDTO categoryDTO){
        this.name = categoryDTO.getName();
        this.status = categoryDTO.getStatus();
    }
}
