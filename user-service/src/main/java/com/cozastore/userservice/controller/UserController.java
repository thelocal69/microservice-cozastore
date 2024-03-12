package com.cozastore.userservice.controller;

import com.cozastore.userservice.annotation.RequiredAuthorization;
import com.cozastore.userservice.dto.BanUserDTO;
import com.cozastore.userservice.dto.ChangePasswordDTO;
import com.cozastore.userservice.dto.UserDetailDTO;
import com.cozastore.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/profile/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getProfile(@PathVariable Long id){
        log.info("Get profile is completed");
        return userService.getInformationUser(id);
    }

    @RequiredAuthorization("ROLE_USER")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getUserById(@PathVariable Long id){
        log.info("Get user is completed");
        return userService.getUserById(id);
    }

    @RequiredAuthorization("ROLE_USER")
    @GetMapping("/check/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> checkUserId(@PathVariable Long userId){
        log.info("Check user is completed");
        return userService.getIdUser(userId);
    }

    @RequiredAuthorization("ROLE_USER")
    @PostMapping("/edit")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> editProfile(@RequestBody UserDetailDTO userDetailDTO){
        log.info("Edit profile is completed");
        return userService.editProfileUser(userDetailDTO);
    }

    @RequiredAuthorization("ROLE_USER")
    @PostMapping("/change_password")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO){
        log.info("Change password is completed");
        return userService.changePassword(changePasswordDTO);
    }

    @RequiredAuthorization("ROLE_ADMIN")
    @PostMapping("/banned")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> banned(@RequestBody BanUserDTO banUserDTO){
        log.info("Ban user is completed");
        return userService.banUser(banUserDTO);
    }
}
