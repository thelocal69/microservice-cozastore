package com.cozastore.blogservice.feign;

import com.cozastore.blogservice.dto.TokenDTO;
import com.cozastore.blogservice.payload.ResponseToken;
import feign.Headers;
import feign.RequestLine;

public interface AuthClient {
    @RequestLine("POST /api/account/validate")
    @Headers("Content-Type: application/json")
    ResponseToken getData(TokenDTO tokenDTO);
}
