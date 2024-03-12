package com.cozastore.userservice.converter;

import com.cozastore.userservice.dto.UserDTO;
import com.cozastore.userservice.dto.UserDetailDTO;
import com.cozastore.userservice.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserConverter {
    public UserDTO toUserDTO(UserEntity userEntity){
        return UserDTO
                .builder()
                .id(userEntity.getId())
                .avatarUrl(userEntity.getAvatarUrl())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .fullName(userEntity.getFullName())
                .username(userEntity.getUsername())
                .phone(userEntity.getPhone())
                .isEnable(userEntity.isEnable())
                .status(userEntity.getStatus())
                .createdBy(userEntity.getCreatedBy())
                .createdDate(userEntity.getCreatedDate())
                .modifiedBy(userEntity.getLastModifiedBy())
                .modifiedDate(userEntity.getLastModifiedDate())
                .build();
    }

    public List<UserDTO> toUserDTOList(List<UserEntity> userEntityList){
        return userEntityList.stream().map(this::toUserDTO).collect(Collectors.toList());
    }

    public UserDetailDTO toUserDetailDTO(UserEntity userEntity){
        return UserDetailDTO
                .builder()
                .id(userEntity.getId())
                .avatarUrl(userEntity.getAvatarUrl())
                .email(userEntity.getEmail())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .fullName(userEntity.getFullName())
                .username(userEntity.getUsername())
                .phone(userEntity.getPhone())
                .createdDate(userEntity.getCreatedDate())
                .build();
    }

    public UserEntity toUserEntity(UserDetailDTO userDetailDTO, UserEntity user){
        user.setAvatarUrl(userDetailDTO.getAvatarUrl());
        user.setFirstName(userDetailDTO.getFirstName());
        user.setLastName(userDetailDTO.getLastName());
        user.setFullName(userDetailDTO.getFullName());
        user.setUsername(userDetailDTO.getUsername());
        user.setPhone(userDetailDTO.getPhone());
        return user;
    }

}