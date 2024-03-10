package com.cozastore.securityservice.service.impl;

import com.cozastore.securityservice.converter.AccountConverter;
import com.cozastore.securityservice.dto.UserDTO;
import com.cozastore.securityservice.entity.RoleEntity;
import com.cozastore.securityservice.entity.UserEntity;
import com.cozastore.securityservice.payload.ResponseAuthentication;
import com.cozastore.securityservice.repository.IRoleRepository;
import com.cozastore.securityservice.repository.IUserRepository;
import com.cozastore.securityservice.service.IAccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService implements IAccountService {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final AccountConverter accountConverter;

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> registerAccount(UserDTO userDTO) {
        return CompletableFuture.supplyAsync(() -> {
            if (userRepository.existsByEmailAndStatus(userDTO.getEmail(), 1)){
                log.info("Account is exist cannot register !");
                throw new RuntimeException("Account is exist cannot register !");
            }
            if (!roleRepository.existsByRoleName("USER")){
                throw new RuntimeException("Cannot authorization !");
            }
            RoleEntity role = roleRepository.findByRoleName("USER");
            UserEntity user = accountConverter.toUserEntity(userDTO);
            user.setRole(role);
            this.userRepository.save(user);
            return null;
        });
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseAuthentication> loginAccountUser(UserDTO userDTO) {
        return null;
    }
}
