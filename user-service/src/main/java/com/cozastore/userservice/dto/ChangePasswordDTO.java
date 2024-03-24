package com.cozastore.userservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordDTO {
    private String email;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
}
