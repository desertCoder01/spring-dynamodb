package codes.aditya.dynamodb.service.helper;

import codes.aditya.dynamodb.exception.InvalidRequestException;
import codes.aditya.dynamodb.model.dynamodb.entity.PermissionInfo;
import codes.aditya.dynamodb.model.dynamodb.repository.CommonRepository;
import codes.aditya.dynamodb.model.response.CreateRoleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserServiceHelper {

    private final CommonRepository commonRepository;


    public CreateRoleResponse validateCreatePermissionRequest(String roleName, List<String> permissions) {
        if(permissions.size()==0){
            throw new InvalidRequestException("Invalid request : No permissions provided");
        }
        Set<PermissionInfo> infoSet = permissions.stream().map(permit ->
                        new PermissionInfo(roleName.toUpperCase(), permit.toUpperCase(), true))
                .collect(Collectors.toSet());
        List<Object> batchFind = commonRepository.batchLoad(PermissionInfo.class, infoSet);
        System.out.println("batch find success");
        return null;
    }
}
