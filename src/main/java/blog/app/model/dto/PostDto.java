package blog.app.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Setter
@Getter
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

}
