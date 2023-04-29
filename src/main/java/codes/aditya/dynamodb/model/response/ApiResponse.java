package codes.aditya.dynamodb.model.response;

import lombok.Data;

@Data
public class ApiResponse {
    private String message;
    private boolean success;
}
