package codes.aditya.dynamodb.model.request;

import lombok.Data;

@Data
public class CreateSampleRequest {
    private String name;
    private String location;
    private String qualification;
    private String isActive;
}
