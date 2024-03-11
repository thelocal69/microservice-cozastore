package com.cozastore.securityservice.converter;

import com.cozastore.securityservice.dto.RegisterDTO;
import com.cozastore.securityservice.entity.UserEntity;
import com.cozastore.securityservice.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountConverter {

    private final StringUtils stringUtils;
    private final PasswordEncoder passwordEncoder;

    public UserEntity toUserEntity(RegisterDTO registerDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setAvatarUrl("");
        userEntity.setEmail(registerDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userEntity.setPhone("");
        userEntity.setFirstName("");
        userEntity.setLastName("");
        userEntity.setFullName("");
        userEntity.setUsername(stringUtils.getUserNameFormDomain(registerDTO.getEmail()));
        userEntity.setEnable(false);
        userEntity.setStatus(1);
        return  userEntity;
    }
}
