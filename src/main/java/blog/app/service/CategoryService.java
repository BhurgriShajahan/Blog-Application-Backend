package blog.app.service;

import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    //Create Category
    CustomResponseEntity<CategoryDto> create(CategoryDto categoryDto);

    //Fetch all categories
    CustomResponseEntity<List<CategoryDto>> fetchAllCategories();

    //Fetch category by id
    CustomResponseEntity<CategoryDto> fetchCategoryById(Long categoryId);

    //Update category
    CustomResponseEntity<CategoryDto> updateCategory(CategoryDto categoryDto, Long categoryId);

    //Delete category
    CustomResponseEntity<?> deleteCategory(Long categoryId);
}
