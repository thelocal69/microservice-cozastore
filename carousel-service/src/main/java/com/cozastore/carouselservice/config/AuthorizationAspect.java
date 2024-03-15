package com.cozastore.carouselservice.config;

import com.cozastore.carouselservice.annotation.RequiredAuthorization;
import com.cozastore.carouselservice.dto.TokenDTO;
import com.cozastore.carouselservice.exception.ForbiddenException;
import com.cozastore.carouselservice.feign.AuthClient;
import com.cozastore.carouselservice.payload.ResponseToken;
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
            throw new ForbiddenException("Unauthorized");
        }
        try {

            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setAccessToken(token);
            ResponseToken credential = authClient.getData(tokenDTO);
            request.setAttribute("user", credential);
            if (credential == null) {
                throw new ForbiddenException("Unauthorized");
            }
            String roleName = credential.getRoleName();
            if (!roleName.equals(requiredAuthorization.value()[0])){
                throw new ForbiddenException("Don't have permission to do this!");
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
