package com.cozastore.securityservice.service;

import com.cozastore.securityservice.dto.UserDTO;

import java.util.concurrent.CompletableFuture;

public interface IAccountService {
    CompletableFuture<Void> registerAccount(UserDTO userDTO);
    CompletableFuture<Void> loginAccountUser(UserDTO userDTO);
}
