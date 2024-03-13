package com.cozastore.carouselservice.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ICategoryClient {
    @RequestLine("GET /api/category/{categoryId}")
    @Headers({"Authorization: {requester}"})
    Boolean existCategoryId(@Param("requester") String request ,@Param("categoryId") String categoryId);
}
