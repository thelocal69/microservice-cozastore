package com.cozastore.productservice.controller;

import com.cozastore.productservice.dto.ColorDTO;
import com.cozastore.productservice.payload.ResponseObject;
import com.cozastore.productservice.service.IColorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/color")
public class ColorController {

    private final IColorService colorService;

    @GetMapping()
    @Transactional(readOnly = true)
    public ResponseEntity<ResponseObject> getAll(){
        log.info("Get All Color is completed !");
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        new ResponseObject(
                                200,
                                "Get All Color is completed !",
                                this.colorService.getAll()
                        )
                );
    }

    @PostMapping()
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ResponseObject> createColor(@RequestBody ColorDTO colorDTO){
        this.colorService.createColor(colorDTO);
        log.info("Created Color is completed !");
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        new ResponseObject(
                                201,
                                "Created Color is completed !",
                                ""
                        )
                );
    }
}
