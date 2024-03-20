package com.cozastore.userservice.service;

import com.cozastore.userservice.dto.BanUserDTO;
import com.cozastore.userservice.dto.ChangePasswordDTO;
import com.cozastore.userservice.dto.UserDTO;
import com.cozastore.userservice.dto.UserDetailDTO;
import com.cozastore.userservice.payload.ResponseOutput;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.CompletableFuture;

public interface IUserService {
    CompletableFuture<Void> changePassword(ChangePasswordDTO changePasswordDTO, HttpServletRequest request);
    CompletableFuture<String> editProfileUser(UserDetailDTO userDetailDTO, HttpServletRequest request);
    CompletableFuture<Void> banUser(BanUserDTO banUserDTO);
    CompletableFuture<ResponseOutput> getAllUser(int page, int limit);
    CompletableFuture<UserDetailDTO> getInformationUser(HttpServletRequest request);
    CompletableFuture<Boolean> getIdUser(String id);
    CompletableFuture<UserDTO> getUserById(String id);
}
