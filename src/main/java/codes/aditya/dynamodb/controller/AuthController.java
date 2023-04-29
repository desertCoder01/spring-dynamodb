package codes.aditya.dynamodb.controller;

import codes.aditya.dynamodb.model.request.LoginRequest;
import codes.aditya.dynamodb.model.request.SignupRequest;
import codes.aditya.dynamodb.model.response.GenericApiResponse;
import codes.aditya.dynamodb.model.response.LoginResponse;
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

    @PostMapping("/login")
    ResponseEntity<GenericApiResponse<LoginResponse>> login(@RequestBody LoginRequest request){
        GenericApiResponse<LoginResponse> response = authService.login(request);
        response.setUri("/auth/login");
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }
}
