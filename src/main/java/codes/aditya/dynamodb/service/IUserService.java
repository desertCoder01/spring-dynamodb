package codes.aditya.dynamodb.service;

import codes.aditya.dynamodb.model.response.CreateRoleResponse;
import codes.aditya.dynamodb.model.response.GenericApiResponse;

import java.util.List;

public interface IUserService {

    GenericApiResponse<CreateRoleResponse> createRole(String roleName, List<String> permissions);
}
