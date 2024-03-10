package com.cozastore.securityservice.converter;

import com.cozastore.securityservice.dto.UserDTO;
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

    public UserEntity toUserEntity(UserDTO userDTO){
        UserEntity userEntity = new UserEntity();
        userEntity.setAvatarUrl("");
        userEntity.setEmail(userDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userEntity.setPhone("");
        userEntity.setFirstName("");
        userEntity.setLastName("");
        userEntity.setFullName("");
        userEntity.setUsername(stringUtils.getUserNameFormDomain(userDTO.getEmail()));
        userEntity.setEnable(false);
        userEntity.setStatus(1);
        return  userEntity;
    }
}
