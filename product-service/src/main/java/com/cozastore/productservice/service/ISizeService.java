package com.cozastore.productservice.service;

import com.cozastore.productservice.dto.SizeDTO;

import java.util.List;

public interface ISizeService {
    List<SizeDTO> getAll();
    void createSize(SizeDTO sizeDTO);
}
