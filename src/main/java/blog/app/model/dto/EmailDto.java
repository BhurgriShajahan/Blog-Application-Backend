package blog.app.model.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmailDto {

    private String to;
    private String subject;
    private String message;

}
