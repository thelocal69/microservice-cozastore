package com.cozastore.productservice.service.impl;

import com.cozastore.productservice.converter.SizeConverter;
import com.cozastore.productservice.dto.SizeDTO;
import com.cozastore.productservice.repository.ISizeRepository;
import com.cozastore.productservice.service.ISizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SizeService implements ISizeService {

    private final ISizeRepository sizeRepository;
    private final SizeConverter sizeConverter;

    @Override
    @Transactional(readOnly = true)
    public List<SizeDTO> getAll() {
        if (sizeRepository.findAll().isEmpty()){
            log.info("List size is empty !");
            throw new RuntimeException("List size is empty !");
        }
        return sizeConverter.toSizeDTOList(
                sizeRepository.findAll()
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSize(SizeDTO sizeDTO) {
        if (sizeRepository.existsByName(sizeDTO.getName())){
            log.info("Size name is duplicated !");
            throw new RuntimeException("Size name is duplicated !");
        }
        this.sizeRepository.save(
                sizeConverter.toSizeModel(
                        sizeDTO
                )
        );
    }
}
