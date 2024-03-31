package com.cozastore.userservice.controller;

import com.cozastore.userservice.annotation.RequiredAuthorization;
import com.cozastore.userservice.dto.BanUserDTO;
import com.cozastore.userservice.dto.ChangePasswordDTO;
import com.cozastore.userservice.dto.UserDetailDTO;
import com.cozastore.userservice.service.IUserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final IUserService userService;

    @RequiredAuthorization("ROLE_ADMIN")
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAll(
            @RequestParam int page,
            @RequestParam int limit
    ){
        log.info("Get All is completed");
        return userService.getAllUser(page, limit);
    }

    @RequiredAuthorization("ROLE_USER")
    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getProfile(){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Get profile is completed");
        return userService.getInformationUser(request);
    }

    @RequiredAuthorization("ROLE_ADMIN")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getUserById(@PathVariable String id){
        log.info("Get user is completed");
        return userService.getUserById(id);
    }

    @RequiredAuthorization("ROLE_USER")
    @GetMapping("/check/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> checkUserId(@PathVariable String userId){
        log.info("Check user is completed");
        return userService.getIdUser(userId);
    }

    @RequiredAuthorization("ROLE_USER")
    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> editProfile(@RequestBody UserDetailDTO userDetailDTO){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Edit profile is completed");
        return userService.editProfileUser(userDetailDTO, request);
    }

    @RequiredAuthorization("ROLE_USER")
    @PutMapping("/change_password")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Change password is completed");
        return userService.changePassword(changePasswordDTO, request);
    }

    @RequiredAuthorization("ROLE_ADMIN")
    @PutMapping("/banned")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> banned(@RequestBody BanUserDTO banUserDTO){
        log.info("Ban user is completed");
        return userService.banUser(banUserDTO);
    }
}
