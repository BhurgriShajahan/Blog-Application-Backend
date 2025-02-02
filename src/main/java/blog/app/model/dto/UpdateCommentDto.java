package blog.app.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateCommentDto {

    @NotBlank(message ="Content is required")
    private String content;
}
