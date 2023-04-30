package codes.aditya.dynamodb.security;

import codes.aditya.dynamodb.model.AppConstants;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "x-auth-token";
    private final TokenProvider tokenProvider;
    private final SecurityUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("received request with path :" + request.getServletPath());
        response.setHeader("Trace-Id", UUID.randomUUID().toString().replace("-", ""));
        if(checkForSecure(request.getServletPath())){
            filterChain.doFilter(request,response);
        }else {
            String token = request.getHeader(AUTHORIZATION);
            if(StringUtils.hasText(token) && SecurityContextHolder.getContext().getAuthentication()==null){
                try {
                    String username = tokenProvider.getUsernameFromToken(token);
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken usernamePasswordAuthentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthentication);
                }catch (Exception ex){
                    log.info(ex.getMessage());
                    request.setAttribute("error_message",ex.getMessage());
                }
            }
            filterChain.doFilter(request, response);
        }
    }

    private boolean checkForSecure(String servletPath) {
        for(String test : AppConstants.AUTH_WHITELIST){
            if(servletPath.contains(test))
                return true;
        }
        return false;
    }
}
