package com.cozastore.securityservice.controller;

import com.cozastore.securityservice.dto.UserDTO;
import com.cozastore.securityservice.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/account")
public class AccountController {

    private final IAccountService accountService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> register(@RequestBody UserDTO userDTO){
        log.info("register is completed !");
        return this.accountService.registerAccount(userDTO);
    }
}
