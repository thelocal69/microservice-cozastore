package com.cozastore.securityservice.service;

import com.cozastore.securityservice.dto.UserDTO;
import com.cozastore.securityservice.payload.ResponseAuthentication;

import java.util.concurrent.CompletableFuture;

public interface IAccountService {
    CompletableFuture<Void> registerAccount(UserDTO userDTO);
    CompletableFuture<ResponseAuthentication> loginAccountUser(UserDTO userDTO);
}
