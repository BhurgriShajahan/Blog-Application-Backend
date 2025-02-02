package blog.app.controller;

import blog.app.endpoints.CategoryControllerEndpoints;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.CategoryDto;
import blog.app.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class CategoryController implements CategoryControllerEndpoints {

    private final CategoryService categoryService;

    @Override
    public CustomResponseEntity<CategoryDto> create(CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @Override
    public CustomResponseEntity<List<CategoryDto>> fetchAllCategories(){
        return categoryService.fetchAllCategories();
    }

    @Override
    public CustomResponseEntity<CategoryDto> fetchCategoryById(Long categoryId){
        return categoryService.fetchCategoryById(categoryId);
    }

    @Override
    public CustomResponseEntity<CategoryDto> updateCategory(CategoryDto categoryDto, Long categoryId){
        return categoryService.updateCategory(categoryDto,categoryId);
    }

    @Override
    public CustomResponseEntity<?> deleteCategory(Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
