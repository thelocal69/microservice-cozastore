package com.cozastore.cartservice.feign;

import com.cozastore.cartservice.dto.TokenDTO;
import com.cozastore.cartservice.payload.ResponseToken;
import feign.Headers;
import feign.RequestLine;

public interface AuthClient {
    @RequestLine("POST /api/account/validate")
    @Headers("Content-Type: application/json")
    ResponseToken getData(TokenDTO tokenDTO);
}
