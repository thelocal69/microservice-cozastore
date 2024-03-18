package com.cozastore.productservice.service.impl;

import com.cozastore.productservice.converter.ProductConverter;
import com.cozastore.productservice.dto.ProductDTO;
import com.cozastore.productservice.entity.ProductEntity;
import com.cozastore.productservice.exception.NotFoundException;
import com.cozastore.productservice.payload.ResponseOutput;
import com.cozastore.productservice.repository.ICategoryRepository;
import com.cozastore.productservice.repository.IColorRepository;
import com.cozastore.productservice.repository.IProductRepository;
import com.cozastore.productservice.repository.ISizeRepository;
import com.cozastore.productservice.service.IProductService;
import com.cozastore.productservice.util.RedisUtil;
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
public class ProductService implements IProductService {
    private final ProductConverter productConverter;
    private final IProductRepository productRepository;
    private final ISizeRepository sizeRepository;
    private final IColorRepository colorRepository;
    private final ICategoryRepository categoryRepository;
    private final RedisUtil redisUtil;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseOutput> getAllProduct(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        if (this.productRepository.findAll(pageable).isEmpty()){
            log.info("List Product not found !");
            throw new NotFoundException("List Product not found !");
        }
        List<ProductDTO> productDTOList = productConverter.toListProductDTO(
                productRepository.findAll(pageable).getContent()
        );
        int totalItem = (int) productRepository.count();
        int totalPage = (int) Math.ceil((double) totalItem / limit);
        log.info("Get all product is completed !");
        return CompletableFuture.supplyAsync(
                () -> new ResponseOutput(
                        page, totalPage, totalItem, productDTOList
                )
        );
    }

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseOutput> getAllProductByCategory(String categoryId, int page, int limit) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!categoryRepository.existsById(categoryId)){
                        log.info("Category is not exist ! Cannot get all product !");
                        throw new NotFoundException("Category is not exist ! Cannot get all product !");
                    }
                    Pageable pageable = PageRequest.of(page - 1, limit);
                    List<ProductDTO> productDTOList = productConverter.toListProductDTO(
                            productRepository.findAllByCategory_Id(categoryId ,pageable)
                    );
                    if (productDTOList.isEmpty()){
                        log.info("List Product not found !");
                        throw new NotFoundException("List Product not found !");
                    }
                    int totalItem = productRepository.countAllByCategory_Id(categoryId);
                    int totalPage = (int) Math.ceil((double) totalItem / limit);
                    ResponseOutput dataCache = this.redisUtil.getAllRedis(categoryId, "getAllProductCategory", page, limit);
                    if (dataCache == null){
                        log.info("Get all product by category is completed !");
                        ResponseOutput dataDB = ResponseOutput
                                .builder()
                                .page(page)
                                .totalItem(totalItem)
                                .totalPage(totalPage)
                                .data(productDTOList)
                                .build();
                        this.redisUtil.saveToRedis(categoryId, "getAllProductCategory", page, limit, dataDB);
                        return dataDB;
                    }
                    log.info("Get all product by category is completed !");
                    return dataCache;
                }
        );
    }


    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> upsert(ProductDTO productDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    ProductEntity productModel = productConverter.toProductModel(productDTO);
                    this.redisUtil.clear();
                    if (productModel.getId() != null){
                        if (this.productRepository.findById(productDTO.getId()).isEmpty()){
                            log.info("Product not found ! Can not update !");
                            throw new NotFoundException("Product not found ! Can not update !");
                        }
                        if (
                                !categoryRepository.existsByName(productDTO.getCategory()) ||
                                        !sizeRepository.existsByName(productDTO.getSize()) ||
                                        !colorRepository.existsByName(productDTO.getColor())
                        ){
                            log.info("Category or Size or Color is not exist !");
                            throw new NotFoundException("Category or Size or Color is not exist !");
                        }
                               productModel = productConverter.updateProduct(
                                        this.productRepository.findById(productDTO.getId()).get(),
                                        productDTO
                        );
                        log.info("Product id: "+productDTO.getId()+" update successfully !");
                    }
                    if (
                            !categoryRepository.existsByName(productDTO.getCategory()) ||
                                    !sizeRepository.existsByName(productDTO.getSize()) ||
                                    !colorRepository.existsByName(productDTO.getColor())
                    ){
                        log.info("Category or Size or Color is not exist !");
                        throw new NotFoundException("Category or Size or Color is not exist !");
                    }
                    productModel.setCategory(categoryRepository.findByName(productDTO.getCategory()));
                    productModel.setSize(sizeRepository.findByName(productDTO.getSize()));
                    productModel.setColor(colorRepository.findByName(productDTO.getColor()));
                    this.productRepository.save(productModel);
                    log.info("Product id: "+productDTO.getId()+" created successfully !");
                    return null;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> delete(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    this.redisUtil.clear();
                    if (!productRepository.existsById(id)){
                        log.info("Can not delete product ! product not exist !");
                        throw new NotFoundException("Can not delete product ! product not exist !");
                    }
                    this.productRepository.deleteById(id);
                    log.info("Delete product is completed !");
                    return null;
                }
        );
    }

    @Override
    public CompletableFuture<Boolean> getProductId(String id) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!productRepository.existsById(id)){
                        log.info("Product is not exist !");
                        throw new NotFoundException("Product is not exist !");
                    }
                    log.info("Product is existed !");
                    return productRepository.existsById(id);
                }
        );
    }
}
