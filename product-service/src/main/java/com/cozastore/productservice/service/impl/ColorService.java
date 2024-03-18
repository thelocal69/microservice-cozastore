package com.cozastore.productservice.service.impl;

import com.cozastore.productservice.converter.ColorConverter;
import com.cozastore.productservice.dto.ColorDTO;
import com.cozastore.productservice.exception.BadRequestException;
import com.cozastore.productservice.exception.NotFoundException;
import com.cozastore.productservice.payload.ResponseOutput;
import com.cozastore.productservice.repository.IColorRepository;
import com.cozastore.productservice.service.IColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
@RequiredArgsConstructor
public class ColorService implements IColorService {

    private final IColorRepository colorRepository;
    private final ColorConverter colorConverter;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseOutput> getAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        if (colorRepository.findAll(pageable).isEmpty()){
            log.info("List color not found !");
            throw new NotFoundException("List color not found !");
        }
        List<ColorDTO> colorDTOList = colorConverter.toColorDTOList(
                colorRepository.findAll(pageable).getContent()
        );
        int totalItem = (int) colorRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        return CompletableFuture.supplyAsync(
                () -> new ResponseOutput(
                        page, totalPage, totalItem, colorDTOList
                )
        );
    }

    @Async
    @Override
    @Transactional(noRollbackFor = Exception.class)
    public CompletableFuture<Void> createColor(ColorDTO colorDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (colorRepository.existsByName(colorDTO.getName())){
                        log.info("Color name is duplicated !");
                        throw new BadRequestException("Color name is duplicated !");
                    }
                    this.colorRepository.save(
                            colorConverter.toColorModel(
                                    colorDTO
                            )
                    );
                    log.info("Created color is completed !");
                    return  null;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> deleteColor(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!colorRepository.existsById(id)){
                        log.info("Color not exist ! Cannot delete !");
                        throw new NotFoundException("Color not exist ! Cannot delete !");
                    }
                    this.colorRepository.deleteById(id);
                    log.info("Delete color is completed !");
                    return  null;
                }
        );
    }
}
