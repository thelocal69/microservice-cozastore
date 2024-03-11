package com.cozastore.securityservice.service;

import com.cozastore.securityservice.dto.LoginDTO;
import com.cozastore.securityservice.dto.RegisterDTO;
import com.cozastore.securityservice.dto.TokenDTO;
import com.cozastore.securityservice.payload.ResponseAuthentication;
import com.cozastore.securityservice.payload.ResponseToken;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.CompletableFuture;

public interface IAccountService {
    CompletableFuture<Void> registerAccount(RegisterDTO registerDTO);
    CompletableFuture<ResponseAuthentication> loginAccountUser(LoginDTO loginDTO);
    CompletableFuture<ResponseAuthentication> loginAccountAdmin(LoginDTO loginDTO);
    CompletableFuture<ResponseToken> validateToken(TokenDTO tokenDTO);
}
