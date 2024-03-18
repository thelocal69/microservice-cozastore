package com.cozastore.cartservice.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface IProductClient {
    @RequestLine("GET /api/product/{productId}")
    @Headers("Authorization: {requester}")
    Boolean checkProduct(@Param("requester") String requester ,@Param("productId") String productId);
}
