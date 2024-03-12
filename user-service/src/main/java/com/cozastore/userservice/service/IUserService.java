package com.cozastore.userservice.service;

import com.cozastore.userservice.dto.BanUserDTO;
import com.cozastore.userservice.dto.ChangePasswordDTO;
import com.cozastore.userservice.dto.UserDTO;
import com.cozastore.userservice.dto.UserDetailDTO;
import com.cozastore.userservice.payload.ResponseOutput;

import java.util.concurrent.CompletableFuture;

public interface IUserService {
    CompletableFuture<ResponseOutput> getAllUser(int page, int limit);
    CompletableFuture<UserDTO> getUserById(Long id);
    CompletableFuture<String> changePassword(ChangePasswordDTO changePasswordDTO);
    CompletableFuture<UserDetailDTO> getInformationUser(Long id);
    CompletableFuture<String> editProfileUser(UserDetailDTO userDetailDTO);
    CompletableFuture<Boolean> banUser(BanUserDTO banUserDTO);
}
