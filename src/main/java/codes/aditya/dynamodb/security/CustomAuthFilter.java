package codes.aditya.dynamodb.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
public class CustomAuthFilter extends OncePerRequestFilter {

    private static final String AUTHORIZATION = "x-auth-token";

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("received request with path :" + request.getServletPath());
        response.setHeader("Trace-Id", UUID.randomUUID().toString().replace("-", ""));
        filterChain.doFilter(request, response);
    }
}
