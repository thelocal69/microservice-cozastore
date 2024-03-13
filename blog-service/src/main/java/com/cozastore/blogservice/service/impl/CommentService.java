package com.cozastore.blogservice.service.impl;

import com.cozastore.blogservice.converter.CommentConverter;
import com.cozastore.blogservice.dto.CommentDTO;
import com.cozastore.blogservice.entity.BlogEntity;
import com.cozastore.blogservice.entity.CommentEntity;
import com.cozastore.blogservice.feign.IUserClient;
import com.cozastore.blogservice.payload.ResponseOutput;
import com.cozastore.blogservice.repository.IBlogRepository;
import com.cozastore.blogservice.repository.ICommentRepository;
import com.cozastore.blogservice.service.ICommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService implements ICommentService {

    private final IBlogRepository blogRepository;
    private final ICommentRepository commentRepository;
    private final IUserClient userClient;
    private final CommentConverter commentConverter;


    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseOutput> getAll(int page, int limit) {
        return CompletableFuture.supplyAsync(
                () -> {
                    Pageable pageable = PageRequest.of(page - 1, limit);
                    List<CommentDTO> commentDTOList = commentConverter.toCommentDTOList(
                            commentRepository.findAll(pageable).getContent()
                    );
                    if (commentDTOList.isEmpty()){
                        log.info("Comment list is empty !");
                        throw new RuntimeException("Comment list is empty !");
                    }
                    int totalItem = (int) commentRepository.count();
                    int totalPage = (int) Math.ceil((double) totalItem / limit);
                    log.info("Get comment list is completed !");
                    return ResponseOutput
                            .builder()
                            .page(page)
                            .totalItem(totalItem)
                            .totalPage(totalPage)
                            .data(commentDTOList)
                            .build();
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> createComment(CommentDTO commentDTO, HttpServletRequest request) {
        return CompletableFuture.supplyAsync(
                () -> {
                    CommentEntity commentEntity = commentConverter.toCommentEntity(commentDTO);
                    if (!blogRepository.existsById(commentDTO.getBlogId())){
                        log.info("Blog id is not exist ! Cannot create !");
                        throw new RuntimeException("Blog id is not exist ! Cannot create !");
                    }
                    if (!userClient.checkUserId(request.getHeader("Authorization") ,commentEntity.getUserId())){
                        log.info("User id is not exist ! Cannot create !");
                        throw new RuntimeException("User id is not exist ! Cannot create !");
                    }
                    BlogEntity blog = blogRepository.findOneById(commentDTO.getBlogId());
                    commentEntity.setBlogId(blog);
                    this.commentRepository.save(commentEntity);
                    log.info("Create comment is completed !");
                    return  null;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> deleteComment(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!commentRepository.existsById(id)){
                        log.info("Comment is not exist ! Cannot delete !");
                        throw new RuntimeException("Comment is not exist ! Cannot delete !");
                    }
                    this.commentRepository.deleteById(id);
                    log.info("Delete comment is completed !");
                    return null;
                }
        );
    }
}
