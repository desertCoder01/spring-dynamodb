package codes.aditya.dynamodb.service.impl;

import codes.aditya.dynamodb.model.response.CreateRoleResponse;
import codes.aditya.dynamodb.model.response.GenericApiResponse;
import codes.aditya.dynamodb.service.IUserService;
import codes.aditya.dynamodb.service.helper.UserServiceHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService implements IUserService {

    private final UserServiceHelper userServiceHelper;

    @Override
    public GenericApiResponse<CreateRoleResponse> createRole(String roleName, List<String> permissions) {
        CreateRoleResponse response = userServiceHelper.validateCreatePermissionRequest(roleName,permissions);
        return null;
    }
}
