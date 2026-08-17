package bsg.budgeting.ai.tool;

import bsg.budgeting.dto.CategoryDto;
import bsg.budgeting.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CategoryTool {

    private final CategoryService categoryService;

    @Tool(description = "Lista as categorias cadastradas.")
    public List<CategoryDto> listCategories() {
        return categoryService.findAll();
    }

}
