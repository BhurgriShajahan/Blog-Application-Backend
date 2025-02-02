package blog.app.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryDto {

    private Long id;
    @NotBlank(message = "Category title cannot be blank")
    @Size(min = 5, max = 40, message = "title must be between 5 and 40 characters")
    private String categoryTitle;
    @NotBlank(message = "category description cannot be blank")
    @Size(min = 5, max = 50, message = "description must be between 5 and 50 characters")
    private String categoryDescription;
}
