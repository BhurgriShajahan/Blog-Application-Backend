package blog.app.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class PostDto {

    private Long id;

    @NotBlank(message = "Title cannot be blank")
    @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters")
    private String title;

    @NotBlank(message = "Content cannot be blank")
    @Size(min = 10, max = 50, message = "Content must be between 10 and 50 characters")
    private String content;

    private MultipartFile image;

    private String imagePath;

    private Date createAt;
    private Date updateAt;

    @NotBlank(message = "Category id cannot be blank")
    private Long categoryId;

    @NotBlank(message = "User id cannot be blank")
    private Long userId;


    // Getters and Setters


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public @NotBlank(message = "Title cannot be blank") @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters") String getTitle() {
        return title;
    }

    public void setTitle(@NotBlank(message = "Title cannot be blank") @Size(min = 5, max = 50, message = "Title must be between 5 and 50 characters") String title) {
        this.title = title;
    }

    public @NotBlank(message = "Content cannot be blank") @Size(min = 10, max = 50, message = "Content must be between 10 and 50 characters") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "Content cannot be blank") @Size(min = 10, max = 50, message = "Content must be between 10 and 50 characters") String content) {
        this.content = content;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    public @NotBlank(message = "Category id cannot be blank") Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(@NotBlank(message = "Category id cannot be blank") Long categoryId) {
        this.categoryId = categoryId;
    }

    public @NotBlank(message = "User id cannot be blank") Long getUserId() {
        return userId;
    }

    public void setUserId(@NotBlank(message = "User id cannot be blank") Long userId) {
        this.userId = userId;
    }
}
