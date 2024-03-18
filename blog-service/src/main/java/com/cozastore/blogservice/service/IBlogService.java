package com.cozastore.blogservice.service;

import com.cozastore.blogservice.dto.BlogDTO;
import com.cozastore.blogservice.payload.ResponseOutput;
import jakarta.servlet.http.HttpServletRequest;

import java.util.concurrent.CompletableFuture;

public interface IBlogService {
    CompletableFuture<ResponseOutput> getAll(int page, int limit);
    CompletableFuture<Void> createOrUpdateBlog(BlogDTO blogDTO, HttpServletRequest request);
    CompletableFuture<Void> deleteBlog(String id);
}
