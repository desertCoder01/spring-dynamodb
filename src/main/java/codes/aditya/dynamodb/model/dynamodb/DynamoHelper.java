package codes.aditya.dynamodb.model.dynamodb;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.model.BillingMode;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.ResourceInUseException;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class DynamoHelper {

    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    @Autowired
    private DynamoDBMapper dynamoDBMapper;

    // it will create all the tables of any package in on demand billing mode
    // it will also create corresponding index if defined inside any table class
//    @PostConstruct
    public void createTable()
            throws IOException, ClassNotFoundException {
        log.info("Inside create table method");
        for (Class<?> clazz : getEntityClasses("codes.aditya.dynamodb.model.dynamodb.entity")) {
            try {
                CreateTableRequest createTableRequest = dynamoDBMapper.generateCreateTableRequest(clazz)
                        .withBillingMode(BillingMode.PAY_PER_REQUEST);
                CreateTableResult createTableResult = amazonDynamoDB.createTable(createTableRequest);
                System.out.println("Created table for " + clazz.getSimpleName());
            } catch (ResourceInUseException e) {
                System.out.println("Table for " + clazz.getSimpleName() + " already exists. Skipping...");
                continue;
            }
        }
        System.out.println("Table creation completed");
    }

    private List<Class<?>> getEntityClasses(String packageName) throws IOException, ClassNotFoundException {
        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(DynamoDBTable.class));
        List<Class<?>> classes = new ArrayList<>();
        for (BeanDefinition bd : scanner.findCandidateComponents(packageName)) {
            Class<?> clazz = Class.forName(bd.getBeanClassName());
            classes.add(clazz);
        }
        return classes;
    }
}
