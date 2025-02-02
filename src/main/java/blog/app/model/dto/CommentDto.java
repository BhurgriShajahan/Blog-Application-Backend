package blog.app.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {

    private Long id;

    @NotBlank(message = "Comment content is required!")
    private String content;
    private Long postId;

    @NotNull(message = "User ID is required!")
    private Long userId;
}
