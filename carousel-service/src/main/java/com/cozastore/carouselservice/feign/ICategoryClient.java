package com.cozastore.carouselservice.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface ICategoryClient {
    @RequestLine("GET /api/category/{categoryId}")
    @Headers({"X-API-KEY: {requester}", "Content-Type: application/json"})
    Boolean existCategoryId(@Param("categoryId") String categoryId);
}
