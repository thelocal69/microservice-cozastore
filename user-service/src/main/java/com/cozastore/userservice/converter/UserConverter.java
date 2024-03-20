package com.cozastore.userservice.converter;

import com.cozastore.userservice.dto.SendUserDTO;
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

    public UserEntity toUserEntity(SendUserDTO sendUserDTO){
        UserEntity user = new UserEntity();
        user.setUsername(sendUserDTO.getUsername());
        user.setEmail(sendUserDTO.getEmail());
        user.setAvatarUrl("http://res.cloudinary.com/detvyr8w4/image/upload/v1710460378/lej4s77f8pbp4c9bs84v.pnghttp://res.cloudinary.com/detvyr8w4/image/upload/v1710460378/lej4s77f8pbp4c9bs84v.png");
        user.setFullName("");
        user.setFirstName("");
        user.setLastName("");
        user.setPhone("");
        user.setStatus(sendUserDTO.getStatus());
        user.setEnable(sendUserDTO.isEnable());
        user.setUserId(sendUserDTO.getId());
        return  user;
    }

}