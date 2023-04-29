package codes.aditya.dynamodb.service.impl;

import codes.aditya.dynamodb.model.dynamodb.repository.CommonRepository;
import codes.aditya.dynamodb.model.request.SignupRequest;
import codes.aditya.dynamodb.model.response.GenericApiResponse;
import codes.aditya.dynamodb.model.response.SignupResponse;
import codes.aditya.dynamodb.service.IAuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthService implements IAuthService {

    private final CommonRepository commonRepository;

    @Override
    public GenericApiResponse<SignupResponse> signup(SignupRequest request) {
        return null;
    }
}
