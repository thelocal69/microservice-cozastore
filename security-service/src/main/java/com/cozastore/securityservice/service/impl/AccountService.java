package com.cozastore.securityservice.service.impl;

import com.cozastore.securityservice.converter.AccountConverter;
import com.cozastore.securityservice.dto.*;
import com.cozastore.securityservice.entity.RoleEntity;
import com.cozastore.securityservice.entity.TokenEntity;
import com.cozastore.securityservice.entity.UserEntity;
import com.cozastore.securityservice.entity.VerificationTokenEntity;
import com.cozastore.securityservice.exception.AccountException;
import com.cozastore.securityservice.exception.ForbiddenException;
import com.cozastore.securityservice.exception.NotFoundException;
import com.cozastore.securityservice.payload.ResponseAuthentication;
import com.cozastore.securityservice.payload.ResponseToken;
import com.cozastore.securityservice.producer.DataProducer;
import com.cozastore.securityservice.repository.IRefreshTokenRepository;
import com.cozastore.securityservice.repository.IRoleRepository;
import com.cozastore.securityservice.repository.IUserRepository;
import com.cozastore.securityservice.repository.IVerifyTokenRepository;
import com.cozastore.securityservice.service.IAccountService;
import com.cozastore.securityservice.util.JwtUtil;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements IAccountService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IRefreshTokenRepository refreshTokenRepository;
    private final IVerifyTokenRepository verifyTokenRepository;
    private final AccountConverter accountConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final Gson gson;
    private final PasswordEncoder passwordEncoder;
    private final DataProducer dataProducer;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key_register}")
    private String routingKey;

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> registerAccount(RegisterDTO registerDTO) {
        return CompletableFuture.supplyAsync(() -> {
            if (userRepository.existsByEmailAndStatus(registerDTO.getEmail(), 1)){
                log.info("Account is exist cannot register !");
                throw new NotFoundException("Account is exist cannot register !");
            }
            if (userRepository.existsByEmailAndStatus(registerDTO.getEmail(), 0)){
                log.info("Account is block !");
                throw new AccountException("Account is block !");
            }
            if (!roleRepository.existsByRoleName("ROLE_USER")){
                throw new ForbiddenException("Cannot authorization !");
            }
            RoleEntity role = roleRepository.findByRoleName("ROLE_USER");
            UserEntity user = accountConverter.toUserEntity(registerDTO);
            user.setRole(role);
            this.userRepository.save(user);
            String verifyCode = String.format("%040d", new BigInteger(
                    UUID.randomUUID().toString().replace("-", ""), 16)
            );
            VerificationTokenEntity verificationToken = new VerificationTokenEntity(verifyCode.substring(0, 6), user);
            this.verifyTokenRepository.save(verificationToken);
            log.info("Register is completed !");
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
                    if (userRepository.existsByEmailAndStatus(loginDTO.getEmail(), 0)){
                        log.info("User is banned !");
                        throw new AccountException("User is banned !");
                    }
                    UserEntity user = userRepository.findByEmailAndStatus(loginDTO.getEmail(), 1);
                    if (!user.getRole().getRoleName().equals("ROLE_USER")){
                        log.info("Permission denied !");
                        throw new ForbiddenException("Permission denied !");
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
                        throw new ForbiddenException("Permission denied !");
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
                        throw new AccountException("Token is null !");
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

    public String validateCode(String verifyToken) {
        VerificationTokenEntity verificationToken = verifyTokenRepository.findByToken(verifyToken);
        if(verificationToken == null){
            log.info("Invalid verification token !");
            return "Invalid verification token !";
        }
        if (verificationToken.getUser().isEnable()){
            log.info("This account has already been verified, please login!");
            return "This account has already been verified, please, login.";
        }
        UserEntity userEntity = verificationToken.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((verificationToken.getExpired().getTime() - calendar.getTime().getTime()) <= 0){
            verifyTokenRepository.delete(verificationToken);
            log.info("Token already expired !");
            return "Token already expired !";
        }
        userEntity.setEnable(true);
        userRepository.save(userEntity);
        log.info("Token valid");
        return "Valid";
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<String> verifyAccount(VerifyAccountDTO verifyAccountDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    VerificationTokenEntity verificationToken = verifyTokenRepository.findByToken(verifyAccountDTO.getVerifyCode());
                    if (verificationToken.getUser().isEnable()){
                        log.info("This account has already been verified, please login!");
                        return "This account has already been verified, please, login.";
                    }
                    String verificationResult = validateCode(verificationToken.getToken());
                    if (verificationResult.equalsIgnoreCase("Valid")){
                        log.info("Email verified successfully. Now you can login to your account");
                        UserEntity userEntity = userRepository.findByEmail(verificationToken.getUser().getEmail());
                        SendUserDTO sendUserDTO = accountConverter.toSendUserDTO(userEntity);
                        String message = gson.toJson(
                                sendUserDTO
                        );
                        this.dataProducer.sendMessage(exchange, routingKey, message);
                        return "Email verified successfully. Now you can login to your account";
                    }
                    log.info("Invalid verification token !");
                    return "Invalid verification token !";
                }
        );
    }

    public String validateTokenReset(String verifyToken) {
        VerificationTokenEntity tokenReset = verifyTokenRepository.findByToken(verifyToken);
        if(tokenReset == null){
            log.info("Invalid verification token !");
            return "Invalid verification token !";
        }
        Calendar calendar = Calendar.getInstance();
        if ((tokenReset.getExpired().getTime() - calendar.getTime().getTime()) <= 0){
            this.verifyTokenRepository.delete(tokenReset);
            log.info("Token already expired !");
            return "Token already expired !";
        }
        log.info("Token valid");
        return "Valid";
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<String> forgotPassword(String email) {
        return CompletableFuture.supplyAsync(
                () -> {
                    UserEntity user = userRepository.findByEmailAndStatus(email, 1);
                    if (user == null){
                        log.info("User not exist !");
                        return "User not exist !";
                    }
                    if (user.getStatus() == 0){
                        log.info("User is blocked !");
                        return "User is blocked !";
                    }
                    String verifyCode = String.format("%040d", new BigInteger(
                            UUID.randomUUID().toString().replace("-", ""), 16)
                    );
                    VerificationTokenEntity verificationToken = new VerificationTokenEntity(verifyCode.substring(0, 6), user);
                    this.verifyTokenRepository.save(verificationToken);
                    log.info("Check your email to reset password if account was registered !");
                    return "Check your email to reset password if account was register !";
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<String> setPassword(ResetPasswordDTO resetPasswordDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    UserEntity user = userRepository.findByEmailAndStatus(resetPasswordDTO.getEmail(), 1);
                    if (user == null){
                        log.info("User not exist !");
                        return "User not exist !";
                    }
                    if (user.getStatus() == 0){
                        log.info("User is blocked !");
                        return "User is blocked !";
                    }
                    String verificationResult = validateTokenReset(resetPasswordDTO.getToken());
                    if (verificationResult.equalsIgnoreCase("valid")) {
                        user.setPassword(this.passwordEncoder.encode(resetPasswordDTO.getPassword()));
                        this.userRepository.save(user);
                    }else {
                        return "Verify code is not correct !";
                    }
                    log.info("Set new password successfully !");
                    return "Set new password successfully !";
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<String> resendActiveAccount(String email) {
        return CompletableFuture.supplyAsync(
                () -> {
                    UserEntity user = userRepository.findByEmailAndStatus(email, 1);
                    if (user == null){
                        log.info("User not exist !");
                        return "User not exist !";
                    }
                    if (user.getStatus() == 0){
                        log.info("User is blocked !");
                        return "User is blocked !";
                    }
                    String verifyCode = String.format("%040d", new BigInteger(
                            UUID.randomUUID().toString().replace("-", ""), 16)
                    );
                    VerificationTokenEntity verificationToken = new VerificationTokenEntity(verifyCode.substring(0, 6), user);
                    this.verifyTokenRepository.save(verificationToken);
                    return "Verification code is resend to your email ! please check email to activation account again !";
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<ResponseAuthentication> genAccessToken(AccessRefreshTokenDTO accessRefreshTokenDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (jwtUtil.isTokenExpired(accessRefreshTokenDTO.getAccessToken())){
                        try {
                            Claims data = jwtUtil.parserToken(accessRefreshTokenDTO.getRefreshToken());
                            if (data == null){
                                throw new AccountException("AccessToken is null !");
                            }
                            if (jwtUtil.isTokenExpired(accessRefreshTokenDTO.getRefreshToken())){
                                boolean isVTokensExpired = refreshTokenRepository.findByToken(accessRefreshTokenDTO.getRefreshToken())
                                        .map(tokens ->
                                                tokens.isExpired() && tokens.isRevoke()
                                        ).orElse(true);
                                throw new AccountException("Refresh Token is expired !" + isVTokensExpired);
                            }
                            boolean isValidTokens = refreshTokenRepository.findByToken(accessRefreshTokenDTO.getRefreshToken())
                                    .map(tokens ->
                                            !tokens.isExpired() && !tokens.isRevoke()
                                    ).orElse(false);

                            if(!isValidTokens){
                                log.info("Refresh token is expired or not exist !");
                                throw new AccountException("Refresh token is expired or not exist !");
                            }
                            String email = data.getSubject();
                            UserEntity user = userRepository.findByEmail(email);
                            if (user == null || user.getStatus() == 0){
                                log.info("User is not exist or blocked !");
                                throw new NotFoundException("User is not exist or blocked !");
                            }
                            ResponseToken responseToken = new ResponseToken();
                            responseToken.setEmail(email);
                            responseToken.setRoleName(user.getRole().getRoleName());
                            String accessToken = jwtUtil.createAccessToken(gson.toJson(responseToken));
                            return ResponseAuthentication
                                    .builder()
                                    .accessToken(accessToken)
                                    .refreshToken(accessRefreshTokenDTO.getRefreshToken())
                                    .build();
                        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        log.info("Access Token is not expired !");
                        throw new AccountException("Access Token is not expired !");
                    }
                }
        );
    }

}
