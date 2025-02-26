package blog.app.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponseEntity<T> {

    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int errorCode;

    private boolean success;

    private String message;

    private T data;

    public CustomResponseEntity(T data, String message) {
        this.success = true;
        this.message = message;
        this.data = data;
    }

    public CustomResponseEntity(String message) {
        this.success = true;
        this.message = message;
    }

    public CustomResponseEntity(int errorCode, String message) {
        this.errorCode = errorCode;
        this.success = false;
        this.message = message;
    }

    public CustomResponseEntity(int errorCode, String message, T data) {
        this.errorCode = errorCode;
        this.success = false;
        this.message = message;
        this.data = data;
    }

    public static CustomResponseEntity<String> errorResponse(Exception exception) {
        return new CustomResponseEntity<>(1, "An error occurred: " + exception.getMessage(), null);
    }

    public static <T> CustomResponseEntity<T> error(String error) {
        CustomResponseEntity<T> response = new CustomResponseEntity<>();
        response.setMessage(error);
        response.setErrorCode(1000);
        response.setSuccess(false);
        return response;
    }

}
