package com.cozastore.securityservice.controller;

import com.cozastore.securityservice.dto.*;
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

    @PostMapping("/verify_email")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> verifyEmail(@RequestBody VerifyAccountDTO verifyAccountDTO){
        log.info("Verify email is completed !");
        return this.accountService.verifyAccount(verifyAccountDTO);
    }

    @PostMapping("/forgot_password")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> forgotPassword(@RequestParam String email){
        log.info("Send forgot password is completed !");
        return this.accountService.forgotPassword(email);
    }

    @PostMapping("/set_password")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> setPassword(@RequestBody ResetPasswordDTO resetPasswordDTO){
        log.info("Reset password is completed !");
        return this.accountService.setPassword(resetPasswordDTO);
    }

    @PostMapping("/resend_active")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> resendActive(@RequestParam String email){
        log.info("Resend active is completed !");
        return this.accountService.resendActiveAccount(email);
    }

    @PostMapping("/gen_access")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> genAccessToken(@RequestBody AccessRefreshTokenDTO accessRefreshTokenDTO){
        log.info("Gen access token is completed !");
        return this.accountService.genAccessToken(accessRefreshTokenDTO);
    }
}
