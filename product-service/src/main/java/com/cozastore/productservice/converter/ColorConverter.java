package com.cozastore.productservice.converter;

import com.cozastore.productservice.dto.ColorDTO;
import com.cozastore.productservice.entity.ColorEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColorConverter {
    public ColorDTO toColorDTO(ColorEntity colorModel){
        return ColorDTO.builder()
                .id(colorModel.getId())
                .name(colorModel.getName())
                .build();
    }

    public List<ColorDTO> toColorDTOList(List<ColorEntity> colorModelList){
        return colorModelList.stream().map(this::toColorDTO).collect(Collectors.toList());
    }

    public ColorEntity toColorModel(ColorDTO colorDTO){
        ColorEntity colorModel = new ColorEntity();
        colorModel.setId(colorDTO.getId());
        colorModel.setName(colorDTO.getName());
        return colorModel;
    }
}
