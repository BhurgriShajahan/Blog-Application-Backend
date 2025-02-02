package blog.app.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdatePostDto {

    private Long id;
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 50, message = "title must be between 5 and 50 characters")
    private String title;
    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, max = 50, message = "Content must be between 10 and 50 characters")
    private String content;
    private String imagePath;

}
