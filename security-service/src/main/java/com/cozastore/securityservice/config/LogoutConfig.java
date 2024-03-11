package com.cozastore.securityservice.config;

import com.cozastore.securityservice.entity.TokenEntity;
import com.cozastore.securityservice.repository.IRefreshTokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutConfig implements LogoutHandler {

    private final IRefreshTokenRepository refreshTokenRepository;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String headerValue = request.getHeader("Authorization");
        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            final String token = headerValue.substring(7);
            TokenEntity storedTokens = refreshTokenRepository.findByToken(token);
            if (storedTokens != null){
                storedTokens.setExpired(true);
                storedTokens.setRevoke(true);
                this.refreshTokenRepository.save(storedTokens);
            }
        }
    }
}
