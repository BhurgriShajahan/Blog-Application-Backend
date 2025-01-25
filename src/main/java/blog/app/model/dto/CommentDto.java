package blog.app.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CommentDto {

    private Long id;

    @NotBlank(message = "Comment content is required!")
    private String content;
    private Long postId;

    @NotNull(message = "User ID is required!")
    private Long userId;

    //Getters & Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public @NotNull(message = "User ID is required!") Long getUserId() {
        return userId;
    }

    public void setUserId(@NotNull(message = "User ID is required!") Long userId) {
        this.userId = userId;
    }
}
