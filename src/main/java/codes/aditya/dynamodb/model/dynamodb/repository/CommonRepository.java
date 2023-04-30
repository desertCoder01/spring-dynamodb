package codes.aditya.dynamodb.model.dynamodb.repository;

import codes.aditya.dynamodb.model.dynamodb.entity.PermissionInfo;
import codes.aditya.dynamodb.model.dynamodb.entity.UserInfo;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.PaginatedQueryList;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CommonRepository<T>  {

    private final DynamoDBMapper dynamoDBMapper;

    public T save(T data){
        dynamoDBMapper.save(data);
        return data;
    }

    public T findByPartitionKey(Class<T> clazz , String key){
        return dynamoDBMapper.load(clazz,key);
    }

    public T findByPartitionAndSortKey(Class<T> clazz , String partitionKey,String sortKey){
        return dynamoDBMapper.load(clazz,partitionKey,sortKey);
    }

    public void delete(Class<T> clazz){
        dynamoDBMapper.delete(clazz);
    }

    public void deleteByKey(Class<T> clazz , String key){
        T entity = findByPartitionKey(clazz, key);
        if(!ObjectUtils.isEmpty(entity))
            dynamoDBMapper.delete(entity);
    }

    public void deleteByPartitionKeyAndSortKey(Class<T> clazz , String partitionKey,String sortKey){
        T entity = findByPartitionAndSortKey(clazz, partitionKey, sortKey);
        if(!ObjectUtils.isEmpty(entity))
            dynamoDBMapper.delete(entity);
    }

    public List<String> fetchPermissionsForRole(String role) {
        Map<String, AttributeValue> eav = new HashMap<>();
        eav.put(":val1",new AttributeValue().withBOOL(true));
        PermissionInfo info = PermissionInfo.builder()
                .role(role)
                .build();
        DynamoDBQueryExpression<PermissionInfo> query = new DynamoDBQueryExpression<PermissionInfo>()
                .withHashKeyValues(info)
                .withFilterExpression("isActive = :val1")
                .withExpressionAttributeValues(eav);
        PaginatedQueryList<PermissionInfo> result = dynamoDBMapper.query(PermissionInfo.class, query);
        return result.stream().map(permit -> permit.getPermission()).collect(Collectors.toList());
    }

    public List<Object> batchLoad(Class<T> clazz, Iterable<? extends Object> itemsToGet) {
        Map<String, List<Object>> batchResponseData = dynamoDBMapper.batchLoad(itemsToGet);
        return batchResponseData.get(dynamoDBMapper.generateCreateTableRequest(clazz).getTableName());
    }
}
