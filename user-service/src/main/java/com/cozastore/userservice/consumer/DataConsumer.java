package com.cozastore.userservice.consumer;

import com.cozastore.userservice.converter.UserConverter;
import com.cozastore.userservice.dto.SendUserDTO;
import com.cozastore.userservice.entity.UserEntity;
import com.cozastore.userservice.exception.AccountException;
import com.cozastore.userservice.exception.NotFoundException;
import com.cozastore.userservice.repository.IUserDetailRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataConsumer {

    private final Gson gson;
    private final IUserDetailRepository userDetailRepository;
    private final UserConverter userConverter;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${rabbitmq.account.queue.name_register}", durable = "true"),
                    exchange = @Exchange(value = "${rabbitmq.account.exchange.name}"),
                    key = "${rabbitmq.account.routing.key_register}"
            )
    )
    public void processQueueUserInfo(String message){
        try {
            SendUserDTO sendUserDTO = gson.fromJson(
                    message, SendUserDTO.class
            );
            if (sendUserDTO == null){
                log.info("Not found data ! Cannot create user info !");
                throw new NotFoundException("Not found data ! Cannot create user info !");
            }
            UserEntity user = userConverter.toUserEntity(sendUserDTO);
            this.userDetailRepository.save(user);
        }catch (AccountException ex){
            log.info(ex.getMessage());
        }
    }
}
