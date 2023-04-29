package codes.aditya.dynamodb.service.impl;

import codes.aditya.dynamodb.exception.InvalidRequestException;
import codes.aditya.dynamodb.model.AppConstants;
import codes.aditya.dynamodb.model.request.LoginRequest;
import codes.aditya.dynamodb.model.request.SignupRequest;
import codes.aditya.dynamodb.model.response.GenericApiResponse;
import codes.aditya.dynamodb.model.response.LoginResponse;
import codes.aditya.dynamodb.model.response.SignupResponse;
import codes.aditya.dynamodb.security.PrincipalUserDetails;
import codes.aditya.dynamodb.service.IAuthService;
import codes.aditya.dynamodb.service.helper.AuthServiceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService implements IAuthService {

    private final AuthServiceHelper authServiceHelper;
    private final AuthenticationManager authenticationManager;

    @Override
    public GenericApiResponse<SignupResponse> signup(SignupRequest request) {
        log.info("inside signup method with request :"+request);
        try {
            authServiceHelper.validateSignupRequest(request);
            authServiceHelper.checkForDuplicateUser(request.getEmail());
            authServiceHelper.buildAndSaveUserInfo(request);
            return new GenericApiResponse<>(SignupResponse.builder()
                    .email(request.getEmail())
                    .message("User registered as role : customer")
                    .build());
        }catch (InvalidRequestException ex){
            log.error(ex.getMessage());
            return new GenericApiResponse(AppConstants.BAD_REQUEST_ERROR,ex.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception ex) {
            log.error(ex.getMessage());
            return new GenericApiResponse(AppConstants.RUNTIME_ERROR,ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public GenericApiResponse<LoginResponse> login(LoginRequest request) {
        log.info("Inside login method with request :"+request);
        authServiceHelper.validateLoginRequest(request);
        try{
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),
                    request.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            PrincipalUserDetails userDetails = (PrincipalUserDetails) authentication.getPrincipal();
            LoginResponse response = authServiceHelper.createLoginResponse(userDetails);
            return new GenericApiResponse<>(response);
        }catch (AuthenticationException ex){
            log.info("Error while processing the request due to :"+ex.getMessage());
            return new GenericApiResponse<>(AppConstants.BAD_REQUEST_ERROR,ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }
}
