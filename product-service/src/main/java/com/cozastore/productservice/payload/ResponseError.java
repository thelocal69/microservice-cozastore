package com.cozastore.productservice.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseError {
    private Timestamp timestamp;
    private int statusCode;
    private HttpStatus error;
    private String message;
    private String path;
}
