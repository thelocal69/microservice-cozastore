package com.cozastore.blogservice.feign;

import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface IUserClient {

    @RequestLine("GET /api/user/check/{userId}")
    @Headers("Authorization: {requester}")
    Boolean checkUserId(@Param("requester") String requester ,@Param("userId") Long userId);
}
