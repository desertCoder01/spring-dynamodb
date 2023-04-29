package codes.aditya.dynamodb.model.response;

import lombok.Builder;


@Builder
public class SignupResponse {
    private String email;
    private String message;
}
