package codes.aditya.dynamodb.model.response;

import lombok.Data;

import java.util.HashMap;
import java.util.HashSet;

@Data
public class CreateRoleResponse {
    private String createdBy;
    HashSet<String> success;
    HashMap<String,String> failure;
}
