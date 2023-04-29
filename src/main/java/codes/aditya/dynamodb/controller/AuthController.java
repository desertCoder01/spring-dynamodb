package codes.aditya.dynamodb.controller;

import codes.aditya.dynamodb.model.request.SignupRequest;
import codes.aditya.dynamodb.model.response.GenericApiResponse;
import codes.aditya.dynamodb.model.response.SignupResponse;
import codes.aditya.dynamodb.service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final IAuthService authService;

    @PostMapping("/signup")
    ResponseEntity<GenericApiResponse<SignupResponse>> signup(@RequestBody SignupRequest request){
        GenericApiResponse<SignupResponse> response = authService.signup(request);
        response.setUri("/auth/signup");
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }

    @GetMapping("/first")
    String test(){
        log.info("inside first method :");
        return "Working fine";
    }

    @GetMapping("/second")
    String testNext(){
        log.info("inside second method :");
        return "Working fine: next";
    }
}
