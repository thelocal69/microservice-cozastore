package com.cozastore.blogservice.service;

import com.cozastore.blogservice.dto.CommentDTO;
import com.cozastore.blogservice.payload.ResponseOutput;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ICommentService {
    CompletableFuture<ResponseOutput> getAll(int page, int limit);
    CompletableFuture<Void> createComment(CommentDTO commentDTO, HttpServletRequest request);
    CompletableFuture<Void> deleteComment(String id);
}
