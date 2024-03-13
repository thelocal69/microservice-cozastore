package com.cozastore.blogservice.service.impl;

import com.cozastore.blogservice.converter.BlogConverter;
import com.cozastore.blogservice.dto.BlogDTO;
import com.cozastore.blogservice.entity.BlogEntity;
import com.cozastore.blogservice.feign.IUserClient;
import com.cozastore.blogservice.payload.ResponseOutput;
import com.cozastore.blogservice.repository.IBlogRepository;
import com.cozastore.blogservice.service.IBlogService;
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
public class BlogService implements IBlogService {

    private final IBlogRepository blogRepository;
    private final BlogConverter blogConverter;
    private final IUserClient userClient;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseOutput> getAll(int page, int limit) {
        return CompletableFuture.supplyAsync(
                () -> {
                    Pageable pageable = PageRequest.of(page - 1, limit);
                    List<BlogDTO> blogDTOList = blogConverter.toBlogDTOList(
                            blogRepository.findAll(pageable).getContent()
                    );
                    if (blogDTOList.isEmpty()){
                        log.info("Get list blog is empty !");
                        throw new RuntimeException("Get list blog is empty !");
                    }
                    int totalItem = (int) blogRepository.count();
                    int totalPage = (int) Math.ceil((double) totalItem / limit);
                    return ResponseOutput
                            .builder()
                            .page(page)
                            .totalItem(totalItem)
                            .totalPage(totalPage)
                            .data(blogDTOList)
                            .build();
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> createOrUpdateBlog(BlogDTO blogDTO, HttpServletRequest request) {
        return CompletableFuture.supplyAsync(
                () -> {
                    BlogEntity blogEntity = blogConverter.toBlogEntity(blogDTO);
                    if (blogDTO.getId() != null){
                        if (!this.blogRepository.existsById(blogDTO.getId())){
                            log.info("Blog not found cannot update!");
                            throw new RuntimeException("Blog not found cannot update!");
                        }
                        if (!userClient.checkUserId(request.getHeader("Authorization"), blogEntity.getUserId())){
                            log.info("User id not exist !");
                            throw new RuntimeException("User id not exist !");
                        }
                        blogEntity = blogConverter.blogEntity(this.blogRepository.findOneById(blogDTO.getId()), blogDTO);
                        log.info("Update blog is completed !");
                    }
                    if (!userClient.checkUserId(request.getHeader("Authorization"), blogEntity.getUserId())){
                        log.info("User id not exist !");
                        throw new RuntimeException("User id not exist !");
                    }
                    this.blogRepository.save(blogEntity);
                    log.info("Create blog is completed !");
                    return null;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> deleteBlog(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!this.blogRepository.existsById(id)){
                        log.info("Blog not exist ! Cannot delete !");
                        throw new RuntimeException("Blog not exist ! Cannot delete !");
                    }
                    log.info("Delete blog is completed !");
                    this.blogRepository.deleteById(id);
                    return null;
                }
        );
    }
}
