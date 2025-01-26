package blog.app.endpoints;

import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.CategoryDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/v1/category/")
public interface CategoryControllerEndpoints {


     //http://localhost:8090/v1/category/create
    //Create category
    @PostMapping("create")
    CustomResponseEntity<CategoryDto> create(@Valid @RequestBody CategoryDto categoryDto);


    //http://localhost:8090/v1/category/all
    //fetch all categories
    @GetMapping("all")
    CustomResponseEntity<List<CategoryDto>> fetchAllCategories();

    //http://localhost:8090/v1/category/fetch/1
    //Fetch category details
    @GetMapping("fetch/{categoryId}")
    CustomResponseEntity<CategoryDto> fetchCategoryById(@PathVariable Long categoryId);

    //http://localhost:8090/v1/category/update/1
    //Update category
    @PutMapping("update/{categoryId}")
    CustomResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto, @PathVariable Long categoryId);

    //http://localhost:8090/v1/category/delete/3
    //Delete Category
    @DeleteMapping("delete/{categoryId}")
    CustomResponseEntity<?> deleteCategory(@PathVariable Long categoryId);
}
