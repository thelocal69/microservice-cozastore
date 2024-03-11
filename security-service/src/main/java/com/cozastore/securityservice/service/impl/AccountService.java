package com.cozastore.securityservice.service.impl;

import com.cozastore.securityservice.converter.AccountConverter;
import com.cozastore.securityservice.dto.LoginDTO;
import com.cozastore.securityservice.dto.RegisterDTO;
import com.cozastore.securityservice.dto.TokenDTO;
import com.cozastore.securityservice.entity.RoleEntity;
import com.cozastore.securityservice.entity.TokenEntity;
import com.cozastore.securityservice.entity.UserEntity;
import com.cozastore.securityservice.payload.ResponseAuthentication;
import com.cozastore.securityservice.payload.ResponseToken;
import com.cozastore.securityservice.repository.IRefreshTokenRepository;
import com.cozastore.securityservice.repository.IRoleRepository;
import com.cozastore.securityservice.repository.IUserRepository;
import com.cozastore.securityservice.service.IAccountService;
import com.cozastore.securityservice.util.JwtUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements IAccountService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IRefreshTokenRepository refreshTokenRepository;
    private final AccountConverter accountConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final Gson gson;

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> registerAccount(RegisterDTO registerDTO) {
        return CompletableFuture.supplyAsync(() -> {
            if (userRepository.existsByEmailAndStatus(registerDTO.getEmail(), 1)){
                log.info("Account is exist cannot register !");
                throw new RuntimeException("Account is exist cannot register !");
            }
            if (!roleRepository.existsByRoleName("ROLE_USER")){
                throw new RuntimeException("Cannot authorization !");
            }
            RoleEntity role = roleRepository.findByRoleName("ROLE_USER");
            UserEntity user = accountConverter.toUserEntity(registerDTO);
            user.setRole(role);
            this.userRepository.save(user);
            return null;
        });
    }

    public ResponseAuthentication token(String email, String password){
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                email, password
        );
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<SimpleGrantedAuthority> roles = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
        ResponseToken data = new ResponseToken();
        data.setEmail(email);
        data.setRoleName(roles.get(0).toString());
        String accessToken = jwtUtil.createAccessToken(gson.toJson(data));
        String refreshToken = jwtUtil.createRefreshToken(email);
        UserEntity user = userRepository.findByEmailAndStatus(email, 1);
        revokeAllUserTokens(user);
        saveUserToken(user, refreshToken);
        return ResponseAuthentication
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(UserEntity user, String jwtToken){
        TokenEntity tokens = TokenEntity.builder()
                .user(user)
                .token(jwtToken)
                .tokenType("Bearer")
                .expired(false)
                .revoke(false)
                .build();
        this.refreshTokenRepository.save(tokens);
    }

    private void revokeAllUserTokens(UserEntity user){
        List<TokenEntity> validUserTokens = refreshTokenRepository.findAllByTokenValidByUser(user.getId());
        if (!validUserTokens.isEmpty()){
            validUserTokens.forEach(tokens -> {
                tokens.setExpired(true);
                tokens.setRevoke(true);
            });
        }
        this.refreshTokenRepository.saveAll(validUserTokens);
    }

    @Async
    @Override
    public CompletableFuture<ResponseAuthentication> loginAccountUser(LoginDTO loginDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    UserEntity user = userRepository.findByEmailAndStatus(loginDTO.getEmail(), 1);
                    if (!user.getRole().getRoleName().equals("ROLE_USER")){
                        log.info("Permission denied !");
                        throw new RuntimeException("Permission denied !");
                    }
                    return token(loginDTO.getEmail(), loginDTO.getPassword());
                }
        );
    }

    @Async
    @Override
    public CompletableFuture<ResponseAuthentication> loginAccountAdmin(LoginDTO loginDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    UserEntity user = userRepository.findByEmailAndStatus(loginDTO.getEmail(), 1);
                    if (!user.getRole().getRoleName().equals("ROLE_ADMIN")){
                        log.info("Permission denied !");
                        throw new RuntimeException("Permission denied !");
                    }
                    return token(loginDTO.getEmail(), loginDTO.getPassword());
                }
        );
    }

    @Async
    @Override
    public CompletableFuture<ResponseToken> validateToken(TokenDTO tokenDTO) {
        return CompletableFuture.supplyAsync(() -> {
                try {
                    if (tokenDTO.getAccessToken() == null){
                        log.info("Token is null !");
                        throw new RuntimeException("Token is null !");
                    }
                    Claims data = jwtUtil.parserToken(tokenDTO.getAccessToken());
                    log.info("Token is valid !");
                    return gson.fromJson(data.getSubject(), ResponseToken.class);
                } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                    log.info("Token is invalid !");
                    throw new RuntimeException(e);
                }
        });
    }

}
