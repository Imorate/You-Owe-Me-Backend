package ir.imorate.yom.security.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;


@Getter
@Builder
@JsonInclude(NON_NULL)
public class ApiResponse {

    private LocalDateTime timestamp;
    private String reason;
    private String message;
    private String developerMessage;
    private Object data;

}
