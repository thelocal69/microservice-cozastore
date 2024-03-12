package com.cozastore.mediaservice.feign;

import com.cozastore.mediaservice.dto.TokenDTO;
import com.cozastore.mediaservice.payload.ResponseToken;
import feign.Headers;
import feign.RequestLine;

public interface AuthClient {
    @RequestLine("POST /api/account/validate")
    @Headers("Content-Type: application/json")
    ResponseToken getData(TokenDTO tokenDTO);
}
