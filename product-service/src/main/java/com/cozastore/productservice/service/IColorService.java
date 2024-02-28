package com.cozastore.productservice.service;

import com.cozastore.productservice.dto.ColorDTO;

import java.util.List;

public interface IColorService {
    List<ColorDTO> getAll();
    void createColor(ColorDTO colorDTO);
}
