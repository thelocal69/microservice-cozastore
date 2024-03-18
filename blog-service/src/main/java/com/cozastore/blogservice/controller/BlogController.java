package com.cozastore.blogservice.controller;

import com.cozastore.blogservice.annotation.Authenticate;
import com.cozastore.blogservice.dto.BlogDTO;
import com.cozastore.blogservice.service.IBlogService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RestController
@RequestMapping("/api/blog")
@RequiredArgsConstructor
public class BlogController {

    private final IBlogService blogService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAll(
            @RequestParam int page,
            @RequestParam int limit
    ){
        log.info("Get all blog is completed !");
        return blogService.getAll(page, limit);
    }

    @Authenticate
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> createBlog(@RequestBody BlogDTO blogDTO){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Create blog is completed !");
        return blogService.createOrUpdateBlog(blogDTO, request);
    }

    @Authenticate
    @PutMapping("/edit/{blogId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> editBlog(@PathVariable String blogId, @RequestBody BlogDTO blogDTO){
        blogDTO.setId(blogId);
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Edit blog is completed !");
        return blogService.createOrUpdateBlog(blogDTO, request);
    }

    @Authenticate
    @DeleteMapping("/delete/{blogId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> deleteBlog(@PathVariable String blogId){
        log.info("Delete blog is completed !");
        return blogService.deleteBlog(blogId);
    }
}
