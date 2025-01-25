package blog.app.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoryDto {

    private Long id;
    @NotBlank(message = "Category title cannot be blank")
    @Size(min = 5, max = 40, message = "title must be between 5 and 40 characters")
    private String categoryTitle;
    @NotBlank(message = "category description cannot be blank")
    @Size(min = 5, max = 50, message = "description must be between 5 and 50 characters")
    private String categoryDescription;

    //Setters Getters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Category title cannot be blank") @Size(min = 5, max = 40, message = "title must be between 5 and 40 characters") String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(@NotBlank(message = "Category title cannot be blank") @Size(min = 5, max = 40, message = "title must be between 5 and 40 characters") String categoryTitle) {
        this.categoryTitle = categoryTitle;
    }

    public @NotBlank(message = "category description cannot be blank") @Size(min = 5, max = 50, message = "description must be between 5 and 50 characters") String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(@NotBlank(message = "category description cannot be blank") @Size(min = 5, max = 50, message = "description must be between 5 and 50 characters") String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

}
