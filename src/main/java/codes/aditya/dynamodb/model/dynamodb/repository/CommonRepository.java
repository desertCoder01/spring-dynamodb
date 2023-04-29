package codes.aditya.dynamodb.model.dynamodb.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

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

}
