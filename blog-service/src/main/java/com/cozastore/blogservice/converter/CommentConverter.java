package com.cozastore.blogservice.converter;

import com.cozastore.blogservice.dto.CommentDTO;
import com.cozastore.blogservice.entity.CommentEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentConverter {
    public CommentDTO toCommentDTO(CommentEntity commentEntity){
        return CommentDTO
                .builder()
                .id(commentEntity.getId())
                .content(commentEntity.getContent())
                .blogId(commentEntity.getBlogId().getId())
                .userId(commentEntity.getUserId())
                .status(commentEntity.getStatus())
                .createdDate(commentEntity.getCreatedDate())
                .build();
    }

    public List<CommentDTO> toCommentDTOList(List<CommentEntity> commentEntityList){
        return commentEntityList.stream().map(this::toCommentDTO).collect(Collectors.toList());
    }

    public CommentEntity toCommentEntity(CommentDTO commentDTO){
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setContent(commentDTO.getContent());
        commentEntity.setUserId(commentDTO.getUserId());
        commentEntity.setStatus(commentDTO.getStatus());
        return commentEntity;
    }
}
