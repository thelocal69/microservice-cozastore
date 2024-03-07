package com.cozastore.productservice.converter;

import com.cozastore.productservice.dto.SizeDTO;
import com.cozastore.productservice.entity.SizeEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SizeConverter {
    public SizeDTO toSizeDTO(SizeEntity sizeModel){
        return SizeDTO.builder()
                .id(sizeModel.getId())
                .name(sizeModel.getName())
                .build();
    }

    public List<SizeDTO> toSizeDTOList(List<SizeEntity> sizeModelList){
        return sizeModelList.stream().map(this::toSizeDTO).collect(Collectors.toList());
    }

    public SizeEntity toSizeModel(SizeDTO sizeDTO){
        SizeEntity sizeModel = new SizeEntity();
        sizeModel.setId(sizeDTO.getId());
        sizeModel.setName(sizeDTO.getName());
        return sizeModel;
    }
}
