package codes.aditya.dynamodb.controller;

import codes.aditya.dynamodb.model.response.CreateSampleResponse;
import codes.aditya.dynamodb.model.response.GenericApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sample")
public class SampleController {

    @PostMapping("/create")
    ResponseEntity<GenericApiResponse<CreateSampleResponse>> createSampleData(){
        return null;
    }
}
