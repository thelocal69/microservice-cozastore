package com.cozastore.blogservice.config;

import com.cozastore.blogservice.annotation.RequiredAuthorization;
import com.cozastore.blogservice.dto.TokenDTO;
import com.cozastore.blogservice.feign.AuthClient;
import com.cozastore.blogservice.payload.ResponseToken;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthorizationAspect{

    private final AuthClient authClient;

    @Before("@annotation(requiredAuthorization)")
    public void checkAuthorization(RequiredAuthorization requiredAuthorization){
        String token = getTokenFromRequest();
        if (token == null) {
            throw new RuntimeException("Unauthorized");
        }
        try {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setAccessToken(token);
            ResponseToken credential = authClient.getData(tokenDTO);
            request.setAttribute("user", credential);
            if (credential == null) {
                throw new RuntimeException("Unauthorized");
            }
            String roleName = credential.getRoleName();
            if (!roleName.equals(requiredAuthorization.value()[0])){
                throw new RuntimeException("Don't have permission to do this!");
            }
        } catch (FeignException e) {
            throw new RuntimeException(e.getMessage());
        }


    }

    private String getTokenFromRequest() {
        // Get token from request headers
        HttpServletRequest request = ((ServletRequestAttributes) (Objects.requireNonNull(
                RequestContextHolder.getRequestAttributes()))).getRequest();
        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            return requestTokenHeader.substring(7);
        }
        return null;
    }
}
