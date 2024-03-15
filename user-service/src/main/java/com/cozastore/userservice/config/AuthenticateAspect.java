package com.cozastore.userservice.config;

import com.cozastore.userservice.dto.TokenDTO;
import com.cozastore.userservice.exception.AuthenticateException;
import com.cozastore.userservice.feign.AuthClient;
import com.cozastore.userservice.payload.ResponseToken;
import feign.FeignException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@RequiredArgsConstructor
public class AuthenticateAspect {

    private final AuthClient authClient;

    @Before("@annotation(com.cozastore.userservice.annotation.Authenticate)")
    public void authenticate(){
        String token = getTokenFromRequest();
        if (token == null) {
            throw new AuthenticateException("Unauthenticated");
        }
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setAccessToken(token);
            ResponseToken credential = authClient.getData(tokenDTO);
            request.setAttribute("user", credential);
            if (credential == null) {
                throw new AuthenticateException("Unauthenticated");
            }
        } catch (FeignException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private String getTokenFromRequest() {
        // Get token from request headers
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            return requestTokenHeader.substring(7);
        }
        return null;
    }
}
