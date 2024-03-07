package com.cozastore.carouselservice.converter;

import com.cozastore.carouselservice.dto.CarouselDTO;
import com.cozastore.carouselservice.entity.CarouselEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CarouselConverter {
    public CarouselDTO toCarouselDTO(CarouselEntity carouselEntity){
        return CarouselDTO
                .builder()
                .id(carouselEntity.getId())
                .title(carouselEntity.getTitle())
                .categoryId(carouselEntity.getCategoryId())
                .content(carouselEntity.getContent())
                .imageUrl(carouselEntity.getImageUrl())
                .status(carouselEntity.getStatus())
                .build();
    }

    public List<CarouselDTO> toCarouselDTOList(List<CarouselEntity> carouselEntityList){
        return carouselEntityList.stream().map(this::toCarouselDTO).collect(Collectors.toList());
    }

    public CarouselEntity toCarouselEntity(CarouselDTO carouselDTO){
        CarouselEntity carouselEntity = new CarouselEntity();
        carouselEntity.setTitle(carouselDTO.getTitle());
        carouselEntity.setContent(carouselDTO.getContent());
        carouselEntity.setImageUrl(carouselDTO.getImageUrl());
        carouselEntity.setStatus(carouselDTO.getStatus());
        return carouselEntity;
    }
}
