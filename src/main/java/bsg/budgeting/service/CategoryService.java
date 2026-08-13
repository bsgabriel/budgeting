package bsg.budgeting.service;

import bsg.budgeting.dto.CategoryDto;
import bsg.budgeting.entity.Category;
import bsg.budgeting.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryDto findOrCreate(String description) {
        var normalized = description.trim();

        var category = categoryRepository.findByDescription(normalized)
                .orElseGet(() -> categoryRepository.save(Category.builder()
                        .description(normalized)
                        .build()));

        return CategoryDto.builder()
                .categoryId(category.getCategoryId())
                .description(category.getDescription())
                .build();
    }
}
