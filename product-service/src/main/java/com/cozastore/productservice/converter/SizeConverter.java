package com.cozastore.productservice.converter;

import com.cozastore.productservice.dto.SizeDTO;
import com.cozastore.productservice.model.SizeModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SizeConverter {
    public SizeDTO toSizeDTO(SizeModel sizeModel){
        return SizeDTO.builder()
                .id(sizeModel.getId())
                .name(sizeModel.getName())
                .build();
    }

    public List<SizeDTO> toSizeDTOList(List<SizeModel> sizeModelList){
        return sizeModelList.stream().map(this::toSizeDTO).collect(Collectors.toList());
    }

    public SizeModel toSizeModel(SizeDTO sizeDTO){
        SizeModel sizeModel = new SizeModel();
        sizeModel.setId(sizeDTO.getId());
        sizeModel.setName(sizeDTO.getName());
        return sizeModel;
    }
}
