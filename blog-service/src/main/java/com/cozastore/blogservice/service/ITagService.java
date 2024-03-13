package com.cozastore.blogservice.service;

import com.cozastore.blogservice.dto.TagDTO;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface ITagService {
    CompletableFuture<List<TagDTO>> getAll();
    CompletableFuture<Void> createTag(TagDTO tagDTO, HttpServletRequest request);
    CompletableFuture<Void> deleteTag(String id);
}
