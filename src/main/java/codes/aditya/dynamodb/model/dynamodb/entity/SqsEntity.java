package codes.aditya.dynamodb.model.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "pankaj_sqs_test")
public class SqsEntity {

    @DynamoDBHashKey
    private String id;

    @DynamoDBAttribute
    private String userId;

    @DynamoDBAttribute
    private Long createdAt;

    @DynamoDBAttribute
    @DynamoDBTyped(DynamoDBMapperFieldModel.DynamoDBAttributeType.BOOL)
    private Boolean isActive;
}
