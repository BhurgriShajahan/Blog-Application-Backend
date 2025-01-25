package blog.app.model.dto;

import jakarta.validation.constraints.NotBlank;

public class UpdateCommentDto {

    @NotBlank(message ="Content is required")
    private String content;

    public @NotBlank(message = "Content is required") String getContent() {
        return content;
    }

    public void setContent(@NotBlank(message = "Content is required") String content) {
        this.content = content;
    }
}
