package codes.aditya.dynamodb.controller;

import codes.aditya.dynamodb.model.response.CreateRoleResponse;
import codes.aditya.dynamodb.model.response.GenericApiResponse;
import codes.aditya.dynamodb.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

    private final IUserService userService;

    @PostMapping("/create/role")
    ResponseEntity<GenericApiResponse<CreateRoleResponse>> createRoleWithPermissions(@RequestParam("role") String roleName,
                                                                                     @RequestBody List<String> permissions){
        GenericApiResponse<CreateRoleResponse> response = userService.createRole(roleName,permissions);
        response.setUri("/user/create/role");
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }
}
