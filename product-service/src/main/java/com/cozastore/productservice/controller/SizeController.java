package com.cozastore.productservice.controller;

import com.cozastore.productservice.dto.SizeDTO;
import com.cozastore.productservice.payload.ResponseObject;
import com.cozastore.productservice.service.ISizeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/size")
public class SizeController {

    private final ISizeService sizeService;

    @GetMapping()
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAll(){
        log.info("Get all size is completed !");
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject(
                                200,
                                "Get all size is completed !",
                                this.sizeService.getAll()
                        )
                );
    }

    @PostMapping()
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> createSize(@RequestBody SizeDTO sizeDTO){
        this.sizeService.createSize(sizeDTO);
        log.info("Created size is completed !");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ResponseObject(
                                201,
                                "Created size is completed !",
                                ""
                        )
                );
    }
}
