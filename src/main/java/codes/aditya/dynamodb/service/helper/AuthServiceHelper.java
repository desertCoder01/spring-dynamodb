package codes.aditya.dynamodb.service.helper;

import codes.aditya.dynamodb.exception.InvalidRequestException;
import codes.aditya.dynamodb.model.dynamodb.entity.UserInfo;
import codes.aditya.dynamodb.model.dynamodb.repository.CommonRepository;
import codes.aditya.dynamodb.model.request.LoginRequest;
import codes.aditya.dynamodb.model.request.SignupRequest;
import codes.aditya.dynamodb.model.response.LoginResponse;
import codes.aditya.dynamodb.security.PrincipalUserDetails;
import codes.aditya.dynamodb.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthServiceHelper {

    private final CommonRepository commonRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public void validateSignupRequest(SignupRequest request) {
        if(ObjectUtils.isEmpty(request))
            throw new InvalidRequestException("Invalid request : Empty request");
        if(!StringUtils.hasText(request.getEmail()))
            throw new InvalidRequestException("Invalid request : Empty email");
        if(!StringUtils.hasText(request.getName()))
            throw new InvalidRequestException("Invalid request : Empty name");
        if(!StringUtils.hasText(request.getPassword()))
            throw new InvalidRequestException("Invalid request : Empty password");
        request.setPassword(passwordEncoder.encode(request.getPassword()));
    }

    public boolean checkForDuplicateUser(String email) {
        UserInfo userInfo = (UserInfo) commonRepository.findByPartitionKey(UserInfo.class, email);
        if(!ObjectUtils.isEmpty(userInfo)){
            throw new InvalidRequestException("Invalid request : User already registered with email :"+email);
        }
        return true;
    }

    public void buildAndSaveUserInfo(SignupRequest request) {
        UserInfo userInfo = UserInfo.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .role("CUSTOMER")
                .isActive(true)
                .build();
        commonRepository.save(userInfo);
    }

    public void validateLoginRequest(LoginRequest request) {
        if(ObjectUtils.isEmpty(request))
            throw new InvalidRequestException("Invalid request : Empty request");
        if(!StringUtils.hasText(request.getEmail()))
            throw new InvalidRequestException("Invalid request : Empty email");
        if(!StringUtils.hasText(request.getPassword()))
            throw new InvalidRequestException("Invalid request : Empty password");
    }

    public LoginResponse createLoginResponse(PrincipalUserDetails userDetails) {
        String accessToken = tokenProvider.createAccessToken(userDetails);
        String refreshToken = tokenProvider.createRefreshToken(userDetails);
        return new LoginResponse(accessToken,refreshToken);
    }
}
