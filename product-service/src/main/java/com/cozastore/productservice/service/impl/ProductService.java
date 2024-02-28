package com.cozastore.productservice.service.impl;

import com.cozastore.productservice.converter.ProductConverter;
import com.cozastore.productservice.dto.ProductDTO;
import com.cozastore.productservice.model.ProductModel;
import com.cozastore.productservice.repository.ICategoryRepository;
import com.cozastore.productservice.repository.IColorRepository;
import com.cozastore.productservice.repository.IProductRepository;
import com.cozastore.productservice.repository.ISizeRepository;
import com.cozastore.productservice.service.IProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final ProductConverter productConverter;
    private final IProductRepository productRepository;
    private final ISizeRepository sizeRepository;
    private final IColorRepository colorRepository;
    private final ICategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProduct() {
        if (this.productRepository.findAll().isEmpty()){
            log.info("List Product not found !");
            throw new RuntimeException("List Product not found !");
        }
        return productConverter.toListProductDTO(
                productRepository.findAll()
        );
    }

    @Override
    public ProductDTO getProductById(Long id) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void upsert(ProductDTO productDTO) {
        ProductModel productModel = productConverter.toProductModel(productDTO);
       if (productModel.getId() != null){
           if (this.productRepository.findById(productDTO.getId()).isEmpty()){
               log.info("Product not found ! Can not update !");
               throw new RuntimeException("Product not found ! Can not update !");
           }
           if (
                   !categoryRepository.existsByName(productDTO.getCategory()) ||
                           !sizeRepository.existsByName(productDTO.getSize()) ||
                           !colorRepository.existsByName(productDTO.getColor())
           ){
               log.info("Category or Size or Color is not exist !");
               throw new RuntimeException("Category or Size or Color is not exist !");
           }
           this.productRepository.save(
                   productConverter.updateProduct(
                           this.productRepository.findById(productDTO.getId()).get(),
                           productDTO
                   )
           );
           log.info("Product id: "+productDTO.getId()+" update successfully !");
       }
       if (
               !categoryRepository.existsByName(productDTO.getCategory()) ||
                       !sizeRepository.existsByName(productDTO.getSize()) ||
                       !colorRepository.existsByName(productDTO.getColor())
       ){
           log.info("Category or Size or Color is not exist !");
           throw new RuntimeException("Category or Size or Color is not exist !");
       }
       productModel.setCategory(categoryRepository.findByName(productDTO.getCategory()));
       productModel.setSize(sizeRepository.findByName(productDTO.getSize()));
       productModel.setColor(colorRepository.findByName(productDTO.getColor()));
        this.productRepository.save(productModel);
        log.info("Product id: "+productDTO.getId()+" created successfully !");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(ProductDTO productDTO) {
        if (!productRepository.existsById(productDTO.getId())){
            log.info("Can not delete product ! product not exist !");
            throw new RuntimeException("Can not delete product ! product not exist !");
        }
        this.productRepository.deleteById(productDTO.getId());
        log.info("Delete product is completed !");
    }
}
