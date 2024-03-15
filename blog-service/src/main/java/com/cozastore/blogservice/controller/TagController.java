package com.cozastore.blogservice.controller;

import com.cozastore.blogservice.annotation.RequiredAuthorization;
import com.cozastore.blogservice.dto.TagDTO;
import com.cozastore.blogservice.service.ITagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.CompletableFuture;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/tag")
public class TagController {

    private final ITagService tagService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAll(){
        log.info("Get all tag is completed !");
        return tagService.getAll();
    }

    @RequiredAuthorization("ROLE_ADMIN")
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> createTag(@RequestBody TagDTO tagDTO){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Create Tag is completed !");
        return tagService.createTag(tagDTO, request);
    }

    @RequiredAuthorization("ROLE_ADMIN")
    @DeleteMapping("/{tagId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> deleteTag(@PathVariable String tagId){
        log.info("Delete tag is completed !");
        return tagService.deleteTag(tagId);
    }
}
