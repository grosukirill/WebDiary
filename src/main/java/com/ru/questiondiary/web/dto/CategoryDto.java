package com.ru.questiondiary.web.dto;

import com.ru.questiondiary.web.entity.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class CategoryDto {
    private Long id;
    private String category;

    public static CategoryDto from(Category category) {
        CategoryDto result = new CategoryDto();
        result.setId(category.getId());
        result.setCategory(category.getCategory());
        return result;
    }
}
