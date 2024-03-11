package com.cozastore.securityservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccessRefreshTokenDTO {
    private String accessToken;
    private String refreshToken;
}
