package blog.app.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UpdatePostDto {

    private Long id;
    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 50, message = "title must be between 5 and 50 characters")
    private String title;
    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, max = 50, message = "Content must be between 10 and 50 characters")
    private String content;
    private String imagePath;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Title cannot be blank") @Size(min = 5, max = 50, message = "title must be between 5 and 50 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title cannot be blank") @Size(min = 5, max = 50, message = "title must be between 5 and 50 characters") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Content cannot be blank") @Size(min = 10, max = 50, message = "Content must be between 10 and 50 characters") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "Content cannot be blank") @Size(min = 10, max = 50, message = "Content must be between 10 and 50 characters") String content) {
        this.content = content;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
