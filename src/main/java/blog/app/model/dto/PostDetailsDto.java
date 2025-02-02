package blog.app.model.dto;

import blog.app.model.entities.Comment;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class PostDetailsDto {

    private Long id;
    private String title;
    private String content;
    private String imagePath;
    private Date createAt;
    private Date updateAt;

    private CategoryDto category;
    private UserDetailsDto user;
    private List<Comment> comments;

}
