package com.cozastore.productservice.service.impl;

import com.cozastore.productservice.converter.SizeConverter;
import com.cozastore.productservice.dto.SizeDTO;
import com.cozastore.productservice.payload.ResponseOutput;
import com.cozastore.productservice.repository.ISizeRepository;
import com.cozastore.productservice.service.ISizeService;
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
public class SizeService implements ISizeService {

    private final ISizeRepository sizeRepository;
    private final SizeConverter sizeConverter;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseOutput> getAll(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        if (sizeRepository.findAll(pageable).isEmpty()){
            log.info("List size is empty !");
            throw new RuntimeException("List size is empty !");
        }
       List<SizeDTO> sizeDTOList = sizeConverter.toSizeDTOList(
               sizeRepository.findAll(pageable).getContent()
       );
        int totalItem = (int) sizeRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        return CompletableFuture.supplyAsync(
                () -> new ResponseOutput(
                        page, totalPage, totalItem, sizeDTOList
                )
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> createSize(SizeDTO sizeDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (sizeRepository.existsByName(sizeDTO.getName())){
                        log.info("Size name is duplicated !");
                        throw new RuntimeException("Size name is duplicated !");
                    }
                    this.sizeRepository.save(
                            sizeConverter.toSizeModel(
                                    sizeDTO
                            )
                    );
                    return  null;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> deleteSize(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!sizeRepository.existsById(id)){
                        log.info("Size not exist ! Cannot delete size !");
                        throw new RuntimeException("Size not exist ! Cannot delete size !");
                    }
                    this.sizeRepository.deleteById(id);
                    log.info("Delete size is completed !");
                    return  null;
                }
        );
    }
}
