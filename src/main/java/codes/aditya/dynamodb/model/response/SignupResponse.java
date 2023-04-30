package codes.aditya.dynamodb.model.response;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class SignupResponse {
    private String email;
    private String message;
}
