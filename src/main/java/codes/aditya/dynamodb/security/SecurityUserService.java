package codes.aditya.dynamodb.security;

import codes.aditya.dynamodb.model.dynamodb.entity.UserInfo;
import codes.aditya.dynamodb.model.dynamodb.repository.CommonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SecurityUserService implements UserDetailsService {

    private final CommonRepository commonRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("loading user details for :"+username);
        UserInfo userInfo = (UserInfo) commonRepository.findByPartitionKey(UserInfo.class, username);
        if(ObjectUtils.isEmpty(userInfo)){
            throw new UsernameNotFoundException("User not found with username :"+username);
        }
        if(!userInfo.getIsActive()){
            throw new UsernameNotFoundException("User is not active with username :"+username);
        }
        List<String> permissions = commonRepository.fetchPermissionsForRole(userInfo.getRole());
        return PrincipalUserDetails.createDetail(userInfo,permissions);
    }
}
