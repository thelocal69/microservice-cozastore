package com.cozastore.userservice.service.impl;

import com.cozastore.userservice.converter.UserConverter;
import com.cozastore.userservice.dto.*;
import com.cozastore.userservice.entity.UserEntity;
import com.cozastore.userservice.exception.NotFoundException;
import com.cozastore.userservice.feign.AuthClient;
import com.cozastore.userservice.payload.ResponseOutput;
import com.cozastore.userservice.payload.ResponseToken;
import com.cozastore.userservice.producer.DataProducer;
import com.cozastore.userservice.repository.IUserDetailRepository;
import com.cozastore.userservice.service.IUserService;
import com.google.gson.Gson;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final DataProducer dataProducer;
    private final UserConverter userConverter;
    private final IUserDetailRepository userDetailRepository;
    private final AuthClient authClient;
    private final Gson gson;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key_pwd}")
    private String routingKeyPwd;
    @Value("${rabbitmq.routing.key_ban}")
    private String routingKeyBan;


    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> changePassword(ChangePasswordDTO changePasswordDTO, HttpServletRequest request) {
        return CompletableFuture.supplyAsync(
                () -> {
                    final String requestTokenHeader = request.getHeader("Authorization");
                    String email = "";
                    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                        String token = requestTokenHeader.substring(7);
                        ResponseToken data = authClient.getData(new TokenDTO(token));
                        email = data.getEmail();
                    }
                    changePasswordDTO.setEmail(email);
                    String message = gson.toJson(
                            changePasswordDTO
                    );
                    this.dataProducer.sendMessage(
                            exchange,
                            routingKeyPwd,
                            message);
                    log.info("Change password successfully !");
                    return null;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<String> editProfileUser(UserDetailDTO userDetailDTO, HttpServletRequest request) {
        return CompletableFuture.supplyAsync(
                () -> {
                    final String requestTokenHeader = request.getHeader("Authorization");
                    String email = "";
                    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                        String token = requestTokenHeader.substring(7);
                        ResponseToken data = authClient.getData(new TokenDTO(token));
                        email = data.getEmail();
                    }
                    UserEntity user = userDetailRepository.findOneByEmail(email);
                    if (user == null){
                        log.info("User not found !");
                        return  "User not found !";
                    }
                    this.userDetailRepository.save(
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
    public CompletableFuture<Void> banUser(BanUserDTO banUserDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    String message = gson.toJson(
                            banUserDTO
                    );
                    this.dataProducer.sendMessage(
                            exchange,
                            routingKeyBan,
                            message
                    );
                    return null;
                }
        );
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseOutput> getAllUser(int page, int limit) {
        return CompletableFuture.supplyAsync(
                () -> {
                    Pageable pageable = PageRequest.of(page - 1, limit);
                    List<UserDTO> userDTOList = userConverter.toUserDTOList(
                            userDetailRepository.findAll(pageable).getContent()
                    );
                    if (userDTOList.isEmpty()){
                        log.info("List user is empty !");
                        throw new NotFoundException("List user is empty !");
                    }
                    int totalItem = (int) userDetailRepository.count();
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
    public CompletableFuture<UserDetailDTO> getInformationUser(HttpServletRequest request) {
        return CompletableFuture.supplyAsync(
                () -> {
                    final String requestTokenHeader = request.getHeader("Authorization");
                    String email = "";
                    if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
                        String token = requestTokenHeader.substring(7);
                        ResponseToken data = authClient.getData(new TokenDTO(token));
                        email = data.getEmail();
                    }
                    UserEntity user = userDetailRepository.findOneByEmail(email);
                    if (user == null){
                        log.info("User not found !");
                        throw new NotFoundException("User not found !");
                    }
                    log.info("Get user info is completed !");
                    return userConverter.toUserDetailDTO(
                            user
                    );
                }
        );
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<Boolean> getIdUser(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!userDetailRepository.existsById(id)){
                        log.info("User id is not exist !");
                        throw new NotFoundException("User id is not exist !");
                    }
                    log.info("User id is existed !");
                    return userDetailRepository.existsById(id);
                }
        );
    }

    @Override
    public CompletableFuture<UserDTO> getUserById(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!userDetailRepository.existsById(id)){
                        log.info("User not found !");
                        throw new NotFoundException("User not found !");
                    }
                    log.info("Get user info is completed !");
                    return userConverter.toUserDTO(
                            Objects.requireNonNull(userDetailRepository.findById(id).orElse(null))
                    );
                }
        );
    }
}
