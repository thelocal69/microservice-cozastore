package com.cozastore.carouselservice.feign;

import com.cozastore.carouselservice.dto.TokenDTO;
import com.cozastore.carouselservice.payload.ResponseToken;
import feign.Headers;
import feign.RequestLine;

public interface AuthClient {
    @RequestLine("POST /api/account/validate")
    @Headers("Content-Type: application/json")
    ResponseToken getData(TokenDTO tokenDTO);
}
