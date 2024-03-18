package com.cozastore.orderservice.feign;

import com.cozastore.orderservice.dto.TokenDTO;
import com.cozastore.orderservice.payload.ResponseToken;
import feign.Headers;
import feign.RequestLine;

public interface AuthClient {
    @RequestLine("POST /api/account/validate")
    @Headers("Content-Type: application/json")
    ResponseToken getData(TokenDTO tokenDTO);
}
