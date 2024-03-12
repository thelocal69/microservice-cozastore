package com.cozastore.userservice.service.impl;

import com.cozastore.userservice.converter.UserConverter;
import com.cozastore.userservice.dto.BanUserDTO;
import com.cozastore.userservice.dto.ChangePasswordDTO;
import com.cozastore.userservice.dto.UserDTO;
import com.cozastore.userservice.dto.UserDetailDTO;
import com.cozastore.userservice.entity.UserEntity;
import com.cozastore.userservice.payload.ResponseOutput;
import com.cozastore.userservice.repository.IUserRepository;
import com.cozastore.userservice.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final UserConverter userConverter;
    private final PasswordEncoder passwordEncoder;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseOutput> getAllUser(int page, int limit) {
        return CompletableFuture.supplyAsync(
                () -> {
                    Pageable pageable = PageRequest.of(page - 1, limit);
                    List<UserDTO> userDTOList = userConverter.toUserDTOList(
                            userRepository.findAll(pageable).getContent()
                    );
                    if (userDTOList.isEmpty()){
                        log.info("List user is empty !");
                        throw new RuntimeException("List user is empty !");
                    }
                    int totalItem = (int) userRepository.count();
                    int totalPage = (int) Math.ceil((double) totalItem / limit);
                    log.info("List user is completed !");
                    return new ResponseOutput(
                            page,
                            totalItem,
                            totalPage,
                            userDTOList
                    );
                }
        );
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<UserDTO> getUserById(Long id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!userRepository.existsById(id)){
                        log.info("User not found !");
                        throw new RuntimeException("User not found !");
                    }
                    log.info("Get user info is completed !");
                    return userConverter.toUserDTO(
                            Objects.requireNonNull(userRepository.findById(id).orElse(null))
                    );
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<String> changePassword(ChangePasswordDTO changePasswordDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    UserEntity user = userRepository.findOneById(changePasswordDTO.getId());
                    if (!(passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword()))){
                        log.info("Wrong password !");
                        throw new RuntimeException("Wrong password !");
                    }
                    if (!(changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword()))){
                        log.info("Password won't match !");
                        throw new RuntimeException("Password won't match !");
                    }
                    //update new password
                    user.setPassword(passwordEncoder.encode(changePasswordDTO.getConfirmPassword()));
                    this.userRepository.save(user);
                    log.info("Change password successfully !");
                    return "Change password successfully !";
                }
        );
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<UserDetailDTO> getInformationUser(Long id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!userRepository.existsById(id)){
                        log.info("User not found !");
                        throw new RuntimeException("User not found !");
                    }
                    log.info("Get user info is completed !");
                    return userConverter.toUserDetailDTO(
                            userRepository.findOneById(id)
                    );
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<String> editProfileUser(UserDetailDTO userDetailDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!userRepository.existsById(userDetailDTO.getId())){
                        log.info("User not found !");
                        return  "User not found !";
                    }
                    UserEntity user = userRepository.findOneById(userDetailDTO.getId());
                    this.userRepository.save(
                            userConverter.toUserEntity(
                                    userDetailDTO, user
                            )
                    );
                    log.info("Edit user info in completed !");
                    return "Edit user info in completed !";
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Boolean> banUser(BanUserDTO banUserDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                        UserEntity user = userRepository.findById(banUserDTO.getId()).orElse(null);
                        if (user != null){
                            user.setStatus(banUserDTO.getStatus());
                            this.userRepository.save(user);
                            return true;
                        }
                        return false;
                }
        );
    }
}
