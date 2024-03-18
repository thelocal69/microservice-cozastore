package com.cozastore.securityservice.service;

import com.cozastore.securityservice.dto.*;
import com.cozastore.securityservice.payload.ResponseAuthentication;
import com.cozastore.securityservice.payload.ResponseToken;

import java.util.concurrent.CompletableFuture;

public interface IAccountService {
    CompletableFuture<Void> registerAccount(RegisterDTO registerDTO);
    CompletableFuture<ResponseAuthentication> loginAccountUser(LoginDTO loginDTO);
    CompletableFuture<ResponseAuthentication> loginAccountAdmin(LoginDTO loginDTO);
    CompletableFuture<ResponseToken> validateToken(TokenDTO tokenDTO);
    CompletableFuture<String> verifyAccount(VerifyAccountDTO verifyAccountDTO);
    CompletableFuture<String> forgotPassword(String email);
    CompletableFuture<String> setPassword(ResetPasswordDTO resetPasswordDTO);
    CompletableFuture<String> resendActiveAccount(String email);
    CompletableFuture<ResponseAuthentication> genAccessToken(AccessRefreshTokenDTO accessRefreshTokenDTO);
}
