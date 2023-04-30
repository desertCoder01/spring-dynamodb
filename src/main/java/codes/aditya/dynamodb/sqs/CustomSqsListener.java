package codes.aditya.dynamodb.sqs;

import codes.aditya.dynamodb.model.dynamodb.entity.SqsEntity;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

@Slf4j
@Service
public class CustomSqsListener {

    @Autowired
    private AmazonSQS amazonSQS;

    @Value("${cloud.aws.sqs.queue-name}")
    private String queueName;

    public void send(String message) {
        SendMessageRequest sendMessageRequest = new SendMessageRequest()
                .withQueueUrl(queueName)
                .withMessageBody(message);
        amazonSQS.sendMessage(sendMessageRequest);
    }

    @SqsListener("${cloud.aws.sqs.queue-name}")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
        try {
            SqsEntity sqsEntity = convertJsonToTableObject(message, SqsEntity.class);
            System.out.println(sqsEntity.toString());
        }catch (Exception ex){
            log.info(ex.getMessage());
        }

    }


    public static <T> T convertJsonToTableObject(String json, Class<T> clazz)
            throws IOException, IllegalAccessException, InstantiationException,
            NoSuchMethodException, InvocationTargetException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(json);
        JsonNode newImageNode = rootNode.path("dynamodb").path("NewImage");
        T tableObject = clazz.getDeclaredConstructor().newInstance();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            JsonNode valueNode = newImageNode.path(field.getName());
            if (valueNode.isMissingNode()) continue;
            Object value = null;
            switch (field.getType().getSimpleName()) {
                case "String":
                    value = valueNode.path("S").asText();
                    break;
                case "Boolean":
                case "boolean":
                    value = valueNode.path("BOOL").asBoolean();
                    break;
                case "Integer":
                case "int":
                    value = valueNode.path("N").asInt();
                    break;
                case "Long":
                case "long":
                    value = valueNode.path("N").asLong();
                    break;
                case "Float":
                case "float":
                case "Double":
                case "double":
                    value = valueNode.path("N").asDouble();
                    break;
                case "byte[]":
                    value = valueNode.path("B").binaryValue();
                    break;
            }
            if (value != null) field.set(tableObject, value);
        }
        return tableObject;
    }

}
