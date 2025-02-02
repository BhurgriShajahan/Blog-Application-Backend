package blog.app.model.dto.response;

import blog.app.model.dto.PostDetailsDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Setter
@Getter
public class PostResponseDto {

    private List<PostDetailsDto> content;
    private int pageNumber;
    private int pageSize;
    private Long pageElement;
    private Long totalPages;
    private Boolean lastPage;

}
