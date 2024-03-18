package com.cozastore.userservice.feign;

import com.cozastore.userservice.dto.TokenDTO;
import com.cozastore.userservice.payload.ResponseToken;
import feign.Headers;
import feign.RequestLine;

public interface AuthClient {
    @RequestLine("POST /api/account/validate")
    @Headers("Content-Type: application/json")
    ResponseToken getData(TokenDTO tokenDTO);
}
