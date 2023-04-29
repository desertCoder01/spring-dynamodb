package codes.aditya.dynamodb.configuration;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class DynamoConfig {

    @Value("${app.config.dynamodb.access-key}")
    private String accessKey;

    @Value("${app.config.dynamodb.secret-key}")
    private String secretKey;

    @Value("${app.config.dynamodb.end-point-url}")
    private String endPointUrl;

    @Value("${app.config.dynamodb.region}")
    private String region;

    @Bean
    public AmazonDynamoDB buildAmazonDynamoDB() {
        return AmazonDynamoDBClientBuilder
                .standard()
                .withEndpointConfiguration(
                        new AwsClientBuilder.EndpointConfiguration(endPointUrl, region))
                .withCredentials(new AWSStaticCredentialsProvider(
                        new BasicAWSCredentials(accessKey, secretKey)))
                .build();
    }

    @Bean
    public DynamoDBMapper dynamoDBMapper(AmazonDynamoDB amazonDynamoDB) {
        return new DynamoDBMapper(amazonDynamoDB);
    }

    @Bean
    public DynamoDBMapperConfig dynamoDBMapperConfig() {
        DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        builder.withTypeConverterFactory(DynamoDBTypeConverterFactory.standard());
        builder.setConsistentReads(DynamoDBMapperConfig.ConsistentReads.CONSISTENT);
        builder.withTableNameResolver(DynamoDBMapperConfig.DefaultTableNameResolver.INSTANCE);
        return builder.build();
    }

}
