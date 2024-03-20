package com.cozastore.securityservice.converter;

import com.cozastore.securityservice.dto.RegisterDTO;
import com.cozastore.securityservice.dto.SendUserDTO;
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
        userEntity.setEmail(registerDTO.getEmail());
        userEntity.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userEntity.setUsername(stringUtils.getUserNameFormDomain(registerDTO.getEmail()));
        userEntity.setEnable(false);
        userEntity.setStatus(1);
        return  userEntity;
    }

    public SendUserDTO toSendUserDTO(UserEntity user){
        return SendUserDTO
                .builder()
                .id(user.getId())
                .username(stringUtils.getUserNameFormDomain(user.getEmail()))
                .email(user.getEmail())
                .status(user.getStatus())
                .enable(user.isEnable())
                .build();
    }
}
