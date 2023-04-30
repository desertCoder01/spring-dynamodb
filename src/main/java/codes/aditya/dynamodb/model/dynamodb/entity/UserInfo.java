package codes.aditya.dynamodb.model.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "user_info")
public class UserInfo {

    @DynamoDBHashKey
    private String email;

    @DynamoDBAttribute
    private String password;

    @DynamoDBAttribute
    private String role;

    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    private Boolean isActive;
}
