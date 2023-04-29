package codes.aditya.dynamodb.utils;

import codes.aditya.dynamodb.model.dynamodb.entity.Sample;
import codes.aditya.dynamodb.model.dynamodb.entity.Student;
import codes.aditya.dynamodb.model.dynamodb.repository.CommonRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Slf4j
@Component
public class Initializer {

    @Autowired
    private CommonRepository commonRepository;

    @PostConstruct
    public void run() throws Exception {
        System.out.println("Initialization started ..........");
        Map<Integer, String> localtionMap = Map.of(1, "India", 2, "England",
                3, "USA", 4, "Australia", 5, "Latvia");
        Map<Integer, String> qualificationMap = Map.of(1, "Matriculation", 2, "Intermediate",
                3, "Graduation", 4, "PostGraduation", 5, "PHD");
        try {
            Sample sample = Sample.builder()
                    .email(RandomGenerator.randomEmailAddress())
                    .isActive(true)
                    .legacyId(LocalDateTime.now(ZoneOffset.UTC).toInstant(ZoneOffset.UTC).toEpochMilli())
                    .location(localtionMap.get(new Random().nextInt(5) + 1))
                    .name(RandomGenerator.generateRandomString())
                    .userId(UUID.randomUUID().toString().replace("-", ""))
                    .build();
            Student student = Student.builder()
                    .email(sample.getEmail())
                    .id(sample.getUserId())
                    .qualification(qualificationMap.get(new Random().nextInt(5) + 1))
                    .name(sample.getName())
                    .build();
            commonRepository.save(student);
            commonRepository.save(sample);
        } catch (Exception ex) {
            log.info(ex.getMessage());
        }
        System.out.println("Initialization completed.........");
    }
}
