package blog.app.controller;

import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.CategoryDto;
import blog.app.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/category/")
public class CategoryController {

    CategoryService categoryService;

    @Autowired
    CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    //Create category
    @PostMapping("create")
    CustomResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    //fetch all categories
    @GetMapping("all")
    CustomResponseEntity<List<CategoryDto>> fetchAllCategories(){
        return categoryService.fetchAllCategories();
    }

    //Fetch category details
    @GetMapping("fetch/{categoryId}")
    CustomResponseEntity<CategoryDto> fetchCategoryById(@PathVariable Long categoryId){
        return categoryService.fetchCategoryById(categoryId);
    }

    //Update category
    @PutMapping("update/{categoryId}")
    CustomResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId){
        return categoryService.updateCategory(categoryDto,categoryId);
    }

    //Delete Category
    @DeleteMapping("delete/{categoryId}")
    CustomResponseEntity<?> deleteCategory(@PathVariable Long categoryId){
        return categoryService.deleteCategory(categoryId);
    }
}
