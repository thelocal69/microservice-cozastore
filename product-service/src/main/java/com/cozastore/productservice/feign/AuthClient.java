package com.cozastore.productservice.feign;

import com.cozastore.productservice.dto.TokenDTO;
import com.cozastore.productservice.payload.ResponseToken;
import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthClient {
    @RequestLine("POST /api/account/validate")
    @Headers("Content-Type: application/json")
    ResponseToken getData(TokenDTO tokenDTO);
}
