package com.cozastore.productservice.converter;

import com.cozastore.productservice.dto.ColorDTO;
import com.cozastore.productservice.model.ColorModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ColorConverter {
    public ColorDTO toColorDTO(ColorModel colorModel){
        return ColorDTO.builder()
                .id(colorModel.getId())
                .name(colorModel.getName())
                .build();
    }

    public List<ColorDTO> toColorDTOList(List<ColorModel> colorModelList){
        return colorModelList.stream().map(this::toColorDTO).collect(Collectors.toList());
    }

    public ColorModel toColorModel(ColorDTO colorDTO){
        ColorModel colorModel = new ColorModel();
        colorModel.setId(colorDTO.getId());
        colorModel.setName(colorDTO.getName());
        return colorModel;
    }
}
