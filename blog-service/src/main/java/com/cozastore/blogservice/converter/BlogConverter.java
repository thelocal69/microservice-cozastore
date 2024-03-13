package com.cozastore.blogservice.converter;

import com.cozastore.blogservice.dto.BlogDTO;
import com.cozastore.blogservice.entity.BlogEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BlogConverter {
    public BlogDTO toBlogDTO(BlogEntity blogEntity){
        return BlogDTO
                .builder()
                .id(blogEntity.getId())
                .title(blogEntity.getTitle())
                .content(blogEntity.getContent())
                .imageUrl(blogEntity.getImageUrl())
                .userId(blogEntity.getUserId())
                .status(blogEntity.getStatus())
                .createdBy(blogEntity.getCreatedBy())
                .createdDate(blogEntity.getCreatedDate())
                .build();
    }

    public List<BlogDTO> toBlogDTOList(List<BlogEntity> blogEntityList){
        return blogEntityList.stream().map(this::toBlogDTO).collect(Collectors.toList());
    }

    public BlogEntity toBlogEntity(BlogDTO blogDTO){
        BlogEntity blogEntity = new BlogEntity();
        blogEntity.setTitle(blogDTO.getTitle());
        blogEntity.setImageUrl(blogDTO.getImageUrl());
        blogEntity.setContent(blogDTO.getContent());
        blogEntity.setUserId(blogDTO.getUserId());
        return blogEntity;
    }

    public BlogEntity blogEntity(BlogEntity blogEntity, BlogDTO blogDTO){
        blogEntity.setTitle(blogDTO.getTitle());
        blogEntity.setImageUrl(blogDTO.getImageUrl());
        blogEntity.setContent(blogDTO.getContent());
        blogEntity.setUserId(blogDTO.getUserId());
        return blogEntity;
    }
}
