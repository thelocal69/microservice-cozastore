package com.cozastore.securityservice.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendMessage(String exchange, String routingKey, String message){
      log.info(String.format("Message sent -> %s", message));
      this.rabbitTemplate.convertAndSend(exchange, routingKey, message);
    }
}
