package com.cozastore.blogservice.controller;

import com.cozastore.blogservice.annotation.Authenticate;
import com.cozastore.blogservice.dto.CommentDTO;
import com.cozastore.blogservice.service.ICommentService;
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
@RequestMapping("/api/comment")
@Slf4j
@RequiredArgsConstructor
public class CommentController {
    private final ICommentService commentService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Transactional(readOnly = true)
    public CompletableFuture<?> getAll(
        @RequestParam int page,
        @RequestParam int limit
    ){
        log.info("Get all comment is completed !");
        return commentService.getAll(page, limit);
    }

    @Authenticate
    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> createComment(@RequestBody CommentDTO commentDTO){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.info("Create comment is completed !");
        return commentService.createComment(commentDTO, request);
    }

    @Authenticate
    @DeleteMapping("/delete/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<?> deleteComment(@PathVariable String commentId){
        log.info("Delete comment is completed !");
        return commentService.deleteComment(commentId);
    }
}
