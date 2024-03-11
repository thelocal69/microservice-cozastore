package com.cozastore.securityservice.controller;

import com.cozastore.securityservice.dto.LoginDTO;
import com.cozastore.securityservice.dto.RegisterDTO;
import com.cozastore.securityservice.dto.TokenDTO;
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
    public CompletableFuture<?> register(@RequestBody RegisterDTO registerDTO){
        log.info("register is completed !");
        return this.accountService.registerAccount(registerDTO);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> login(@RequestBody LoginDTO loginDTO){
        log.info("Login is completed !");
        return this.accountService.loginAccountUser(loginDTO);
    }

    @PostMapping("/login_admin")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> loginAdmin(@RequestBody LoginDTO loginDTO){
        log.info("Login admin is completed !");
        return this.accountService.loginAccountAdmin(loginDTO);
    }

    @PostMapping("/validate")
    @ResponseStatus(HttpStatus.OK)
    public CompletableFuture<?> validate(@RequestBody TokenDTO tokenDTO){
        log.info("Validate token is completed !");
        return this.accountService.validateToken(tokenDTO);
    }
}
