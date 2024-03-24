package com.cozastore.securityservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendUserDTO {
    private Long id;
    private String username;
    private String email;
    private int status;
    private boolean enable;
}
