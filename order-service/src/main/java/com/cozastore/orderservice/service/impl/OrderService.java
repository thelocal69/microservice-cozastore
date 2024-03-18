package com.cozastore.orderservice.service.impl;

import com.cozastore.orderservice.converter.OrderConverter;
import com.cozastore.orderservice.dto.OrderDTO;
import com.cozastore.orderservice.entity.OrderEntity;
import com.cozastore.orderservice.exception.NotFoundException;
import com.cozastore.orderservice.feign.IProductClient;
import com.cozastore.orderservice.feign.IUserClient;
import com.cozastore.orderservice.payload.ResponseOutput;
import com.cozastore.orderservice.repository.IOrderRepository;
import com.cozastore.orderservice.service.IOrderService;
import com.cozastore.orderservice.util.RedisUtil;
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
public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository;
    private final OrderConverter orderConverter;
    private final IProductClient productClient;
    private final IUserClient userClient;
    private final RedisUtil redisUtil;

    @Async
    @Override
    @Transactional(readOnly = true)
    public CompletableFuture<ResponseOutput> getAllOrder(Long userId ,int page, int limit, HttpServletRequest request) {
        return CompletableFuture.supplyAsync(
                () -> {
                    if (!userClient.checkUserId(request.getHeader("Authorization") ,userId)){
                        log.info("User id is not exist ! Cannot get all !");
                        throw new NotFoundException("User id is not exist ! Cannot get all !");
                    }
                    Pageable pageable = PageRequest.of(page - 1, limit);
                    List<OrderDTO> orderDTOList = orderConverter.toOrderDTOList(
                            orderRepository.findAllByUserId(userId, pageable)
                    );
                    if (orderDTOList.isEmpty()){
                        log.info("Order list is empty !");
                        throw new NotFoundException("Order list is empty !");
                    }
                    int totalItem = orderRepository.countAllByUserId(userId);
                    int totalPage = (int) Math.ceil((double) totalItem / limit);
                    ResponseOutput dataCache = this.redisUtil.getAllRedis(
                            userId, "getOrderUser", page, limit
                    );
                    if (dataCache == null){
                        log.info("Get list order is completed !");
                        ResponseOutput dataDB = ResponseOutput
                                .builder()
                                .page(page)
                                .totalItem(totalItem)
                                .totalPage(totalPage)
                                .data(orderDTOList)
                                .build();
                        this.redisUtil.saveToRedis(
                                userId, "getOrderUser", page, limit, dataDB
                        );
                        return dataDB;
                    }
                    log.info("Get list order is completed !");
                    return dataCache;
                }
        );
    }

    @Async
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CompletableFuture<Void> createOrder(OrderDTO orderDTO, HttpServletRequest request) {
        return CompletableFuture.supplyAsync(
                () -> {
                    OrderEntity order = orderConverter.toOrderEntity(orderDTO);
                    this.redisUtil.clear();
                    if (productClient.checkProduct(
                           request.getHeader("Authorization") ,orderDTO.getProductId())){
                        order.setProductId(orderDTO.getProductId());
                    }
                    if (userClient.checkUserId(request.getHeader("Authorization") ,orderDTO.getUserId())){
                        order.setUserId(orderDTO.getUserId());
                    }
                    this.orderRepository.save(order);
                    log.info("Created order is completed !");
                    return  null;
                }
        );
    }
}
