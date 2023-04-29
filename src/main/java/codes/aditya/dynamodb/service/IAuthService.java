package codes.aditya.dynamodb.service;

import codes.aditya.dynamodb.model.request.SignupRequest;
import codes.aditya.dynamodb.model.response.GenericApiResponse;
import codes.aditya.dynamodb.model.response.SignupResponse;

public interface IAuthService {

    GenericApiResponse<SignupResponse> signup(SignupRequest request) ;
}
