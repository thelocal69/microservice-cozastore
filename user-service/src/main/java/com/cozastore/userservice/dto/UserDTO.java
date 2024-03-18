package com.cozastore.userservice.dto;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String avatarUrl;
    private String firstName;
    private String lastName;
    private String fullName;
    private String username;
    private String email;
    private String phone;
    private boolean  isEnable;
    private int status;
    private String createdBy;
    private Timestamp createdDate;
    private String modifiedBy;
    private Timestamp modifiedDate;
}
