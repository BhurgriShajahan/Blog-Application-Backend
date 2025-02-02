package blog.app.service.impl;

import blog.app.mapper.CategoryMapper;
import blog.app.model.CustomResponseEntity;
import blog.app.model.dto.CategoryDto;
import blog.app.model.entities.Category;
import blog.app.repository.CategoryRepository;
import blog.app.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CustomResponseEntity<CategoryDto> create(CategoryDto categoryDto) {
        try {
            if (categoryDto == null) {
                logger.warn("Attempted to save a null CategoryDto.");
                return new CustomResponseEntity<>(HttpStatus.BAD_REQUEST.value(), "CategoryDto cannot be null!", null);
            }
            logger.info("Attempting to create category: {}", categoryDto);
            Category category = categoryMapper.dtoToEntity(categoryDto);

            category = categoryRepository.save(category);
            categoryDto = categoryMapper.entityToDto(category);

            logger.info("Category created successfully: {}", categoryDto);
            return new CustomResponseEntity<>(categoryDto, "Category created successfully");
        } catch (Exception e) {
            logger.error("Error creating category: {}", e.getMessage(), e);
            return new CustomResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to create category", null);
        }
    }

    @Override
    public CustomResponseEntity<List<CategoryDto>> fetchAllCategories() {
        try {
            logger.info("Fetching all categories.");
            List<Category> categories = categoryRepository.findAll();

            List<CategoryDto> categoryDtos = categories.stream()
                    .map(categoryMapper::entityToDto)
                    .toList();

            logger.info("Fetched {} categories", categoryDtos.size());
            return new CustomResponseEntity<>(categoryDtos, "Categories fetched successfully");
        } catch (Exception e) {
            logger.error("Error fetching categories: {}", e.getMessage(), e);
            return new CustomResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to fetch categories");
        }
    }


    @Override
    public CustomResponseEntity<CategoryDto> fetchCategoryById(Long categoryId) {
        try {
            logger.info("Fetching category with ID: {}", categoryId);
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            if (categoryOptional.isEmpty()) {
                logger.warn("Category not found with ID: {}", categoryId);
                return new CustomResponseEntity<>(HttpStatus.NOT_FOUND.value(), "Category not found", null);
            }

            CategoryDto categoryDto = categoryMapper.entityToDto(categoryOptional.get());

            logger.info("Fetched category with ID: {}", categoryId);
            return new CustomResponseEntity<>(categoryDto, "Category fetched successfully");
        } catch (Exception e) {
            logger.error("Error fetching category with ID {}: {}", categoryId, e.getMessage(), e);
            return new CustomResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to fetch category", null);
        }
    }

    @Override
    public CustomResponseEntity<CategoryDto> updateCategory(CategoryDto categoryDto, Long categoryId) {
        try {
            logger.info("Attempting to update category with ID: {}", categoryId);

            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            if (categoryOptional.isEmpty()) {
                logger.warn("Category not found with ID: {}", categoryId);
                return new CustomResponseEntity<>(HttpStatus.NOT_FOUND.value(), "Category not found", null);
            }

            Category category = categoryOptional.get();

            category.setCategoryTitle(categoryDto.getCategoryTitle());
            category.setCategoryDescription(categoryDto.getCategoryDescription());

            Category updatedCategory = categoryRepository.save(category);

            CategoryDto updatedCategoryDto = categoryMapper.entityToDto(updatedCategory);

            logger.info("Category updated successfully: {}", updatedCategoryDto);

            return new CustomResponseEntity<>(updatedCategoryDto, "Category updated successfully");
        } catch (Exception e) {
            logger.error("Error updating category with ID {}: {}", categoryId, e.getMessage(), e);
            return new CustomResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to update category", null);
        }
    }

    @Override
    public CustomResponseEntity<?> deleteCategory(Long categoryId) {
        try {
            logger.info("Attempting to delete category with ID: {}", categoryId);
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);
            if (categoryOptional.isEmpty()) {
                logger.warn("Category not found with ID: {}", categoryId);
                return new CustomResponseEntity<>(HttpStatus.NOT_FOUND.value(), "Category not found", null);
            }
            categoryRepository.deleteById(categoryId);
            logger.info("Category deleted successfully with ID: {}", categoryId);
            return new CustomResponseEntity<>("Category deleted successfully");
        } catch (Exception e) {
            logger.error("Error deleting category with ID {}: {}", categoryId, e.getMessage(), e);
            return new CustomResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Failed to delete category", null);
        }
    }
}
