package com.cozastore.blogservice.converter;

import com.cozastore.blogservice.dto.TagDTO;
import com.cozastore.blogservice.entity.TagEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagConverter {
    public TagDTO toTagDTO(TagEntity tagEntity){
        return TagDTO
                .builder()
                .id(tagEntity.getId())
                .name(tagEntity.getName())
                .productId(tagEntity.getProductId())
                .blogId(tagEntity.getBlogId().getId())
                .status(tagEntity.getStatus())
                .build();
    }

    public List<TagDTO> toTagDTOList(List<TagEntity> tagEntityList){
        return tagEntityList.stream().map(this::toTagDTO).collect(Collectors.toList());
    }

    public TagEntity toTagEntity(TagDTO tagDTO){
        TagEntity tagEntity = new TagEntity();
        tagEntity.setName(tagDTO.getName());
        tagEntity.setProductId(tagDTO.getProductId());
        tagEntity.setStatus(tagDTO.getStatus());
        return tagEntity;
    }
}
