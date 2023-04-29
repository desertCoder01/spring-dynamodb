package codes.aditya.dynamodb.model.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@DynamoDBTable(tableName = "sample")
public class Sample {

    @DynamoDBHashKey
    private String userId;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String email;

    @DynamoDBAttribute
    private String location;

    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    private Boolean isActive;

    @DynamoDBAttribute(attributeName = "legacyId")
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "legacyId-index")
    private Long legacyId;
}
