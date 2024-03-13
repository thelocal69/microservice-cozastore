package com.cozastore.blogservice.service.impl;

import com.cozastore.blogservice.converter.TagConverter;
import com.cozastore.blogservice.dto.TagDTO;
import com.cozastore.blogservice.entity.BlogEntity;
import com.cozastore.blogservice.entity.TagEntity;
import com.cozastore.blogservice.feign.IProductClient;
import com.cozastore.blogservice.repository.IBlogRepository;
import com.cozastore.blogservice.repository.ITagRepository;
import com.cozastore.blogservice.service.ITagService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class TagService implements ITagService {

    private final ITagRepository tagRepository;
    private final IBlogRepository blogRepository;
    private final TagConverter tagConverter;
    private final IProductClient productClient;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<List<TagDTO>> getAll() {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (this.tagRepository.findAll().isEmpty()){
                        log.info("Get list tag is empty !");
                        throw new RuntimeException("Get list tag is empty !");
                    }
                    return tagConverter.toTagDTOList(
                            tagRepository.findAll()
                    );
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> createTag(TagDTO tagDTO, HttpServletRequest request) {
        return CompletableFuture.supplyAsync(
                () -> {
                    TagEntity tagEntity = tagConverter.toTagEntity(tagDTO);
                    if (!this.blogRepository.existsById(tagDTO.getBlogId())){
                        log.info("Blog id not exist ! Cannot create !");
                        throw new RuntimeException("Blog id not exist ! Cannot create !");
                    }
                    if (!productClient.checkProduct(request.getHeader("Authorization"), tagDTO.getProductId())){
                        log.info("Product id not exist ! Cannot create !");
                        throw new RuntimeException("Product id not exist ! Cannot create !");
                    }
                    BlogEntity blog = blogRepository.findOneById(tagDTO.getBlogId());
                    tagEntity.setBlogId(blog);
                    this.tagRepository.save(tagEntity);
                    log.info("Create tag is completed !");
                    return null;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> deleteTag(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!tagRepository.existsById(id)){
                        log.info("Tag is not exist ! Cannot delete !");
                        throw new RuntimeException("Tag is not exist ! Cannot delete !");
                    }
                    this.tagRepository.deleteById(id);
                    log.info("Delete tag is complete !");
                    return  null;
                }
        );
    }
}
