package codes.aditya.dynamodb.security;

import codes.aditya.dynamodb.model.dynamodb.entity.UserInfo;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class PrincipalUserDetails implements UserDetails {
    private String username;
    private String password;
    private String role;
    private List<String> permissions;

    public PrincipalUserDetails(String email, String role, List<String> permissions) {
        this.role = role;
        this.permissions = permissions;
        this.username = email;
    }

    public static PrincipalUserDetails createDetail(UserInfo userInfo, List<String> permissions) {
        return new PrincipalUserDetails(userInfo.getEmail(), userInfo.getRole(), permissions);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> set = new HashSet<>();
        set.add(new Authority(role));
        return set;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
