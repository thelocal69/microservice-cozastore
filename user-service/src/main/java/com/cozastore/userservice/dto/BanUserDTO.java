package com.cozastore.userservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BanUserDTO {
    private Long id;
    private int status;
}
