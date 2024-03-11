package com.cozastore.productservice.config;

import com.cozastore.productservice.dto.TokenDTO;
import com.cozastore.productservice.feign.AuthClient;
import com.cozastore.productservice.payload.ResponseToken;
import feign.Feign;
import feign.FeignException;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
public class AuthenticateAspect {

    @Value("${gate-way.host}")
    private String host;
    @Value("${gate-way.port}")
    private String port;

    @Before("@annotation(com.cozastore.productservice.annotation.Authenticate)")
    public void authenticate(){
        AuthClient authClient = Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(AuthClient.class,
                        "%s:%s/".formatted(host, port));
        String token = getTokenFromRequest();
        if (token == null) {
            throw new RuntimeException("Unauthorized");
        }
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setAccessToken(token);
            ResponseToken credential = authClient.getData(tokenDTO);
            if (credential == null) {
                throw new RuntimeException("Unauthorized");
            }
            request.setAttribute("user", credential);
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
