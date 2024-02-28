package com.cozastore.productservice.service.impl;

import com.cozastore.productservice.converter.ColorConverter;
import com.cozastore.productservice.dto.ColorDTO;
import com.cozastore.productservice.repository.IColorRepository;
import com.cozastore.productservice.service.IColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ColorService implements IColorService {

    private final IColorRepository colorRepository;
    private final ColorConverter colorConverter;

    @Override
    @Transactional(readOnly = true)
    public List<ColorDTO> getAll() {
        if (colorRepository.findAll().isEmpty()){
            log.info("List color not found !");
            throw new RuntimeException("List color not found !");
        }
        return colorConverter.toColorDTOList(
                colorRepository.findAll()
        );
    }

    @Override
    @Transactional(noRollbackFor = Exception.class)
    public void createColor(ColorDTO colorDTO) {
        if (colorRepository.existsByName(colorDTO.getName())){
            log.info("Color name is duplicated !");
            throw new RuntimeException("Color name is duplicated !");
        }
        this.colorRepository.save(
                colorConverter.toColorModel(
                        colorDTO
                )
        );
        log.info("Created color is completed !");
    }
}
