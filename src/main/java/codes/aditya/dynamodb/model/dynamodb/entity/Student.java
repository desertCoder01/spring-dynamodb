package codes.aditya.dynamodb.model.dynamodb.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@DynamoDBTable(tableName = "student")
public class Student {

    @DynamoDBHashKey
    private String id;

    @DynamoDBRangeKey
    private String email;

    @DynamoDBAttribute
    private String name;

    @DynamoDBAttribute
    private String qualification;
}
