package com.cozastore.cartservice.service.impl;

import com.cozastore.cartservice.converter.CartConverter;
import com.cozastore.cartservice.dto.CartDTO;
import com.cozastore.cartservice.entity.CartEntity;
import com.cozastore.cartservice.exception.BadRequestException;
import com.cozastore.cartservice.exception.NotFoundException;
import com.cozastore.cartservice.feign.IProductClient;
import com.cozastore.cartservice.feign.IUserClient;
import com.cozastore.cartservice.payload.ResponseOutput;
import com.cozastore.cartservice.repository.ICartRepository;
import com.cozastore.cartservice.service.ICartService;
import jakarta.servlet.http.HttpServletRequest;
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
public class CartService implements ICartService {

    private final ICartRepository cartRepository;
    private final CartConverter cartConverter;
    private final IProductClient productClient;
    private final IUserClient userClient;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseOutput> getAll(Long userId, int page, int limit) {
        return CompletableFuture.supplyAsync(
                () -> {
                    Pageable pageable = PageRequest.of(page - 1, limit);
                    List<CartDTO> cartDTOList = cartConverter.toCartDTOList(
                            cartRepository.findAllByUserId(userId ,pageable)
                    );
                    if (cartDTOList.isEmpty()){
                        log.info("List cart is empty !");
                        throw new NotFoundException("List cart is empty !");
                    }
                    int totalItem = cartRepository.countAllByUserId(userId);
                    int totalPage = (int) Math.ceil((double) totalItem / limit);
                    log.info("Get all cart is completed !");
                    return ResponseOutput
                            .builder()
                            .page(page)
                            .totalItem(totalItem)
                            .totalPage(totalPage)
                            .data(cartDTOList)
                            .build();
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> addCartItem(CartDTO cartDTO, HttpServletRequest request) {
        return CompletableFuture.supplyAsync(
                () -> {
                    CartEntity cartEntity = cartConverter.toCartEntity(cartDTO);
                    if (productClient.checkProduct(request.getHeader("Authorization"), cartDTO.getProductId())){
                        cartEntity.setProductId(cartDTO.getProductId());
                    }
                    if (userClient.checkUserId(request.getHeader("Authorization"), cartDTO.getUserId())){
                        cartEntity.setUserId(cartDTO.getUserId());
                    }
                    log.info("Add cart item is completed !");
                    this.cartRepository.save(cartEntity);
                    return null;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> updateCartItem(CartDTO cartDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    CartEntity cartEntity = cartRepository.findOneByIdAndUserId(cartDTO.getId(), cartDTO.getUserId());
                    if (cartEntity == null){
                        log.info("Cart item is empty !");
                        throw new NotFoundException("Cart item is empty !");
                    }
                    if (cartDTO.getQuantity() < 1){
                        log.info("Cannot add cart item less than 1 !");
                        throw new BadRequestException("Cannot add cart item less than 1 !");
                    }
                    cartEntity.setQuantity(cartDTO.getQuantity());
                    log.info("Update cart is completed !");
                    this.cartRepository.save(cartEntity);
                    return null;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> deleteCartItem(CartDTO cartDTO) {
        return CompletableFuture.supplyAsync(
                () -> {
                    CartEntity cartEntity = cartRepository.findOneByIdAndUserId(cartDTO.getId(), cartDTO.getUserId());
                    if (cartEntity == null){
                        log.info("Cannot delete cart ! cart is empty !");
                        throw new NotFoundException("Cannot delete cart ! cart is empty !");
                    }
                    this.cartRepository.deleteById(cartEntity.getId());
                    return  null;
                }
        );
    }
}
