package com.cozastore.userservice.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.queue.name_changepwd}")
    private String queuePWD;
    @Value("${rabbitmq.queue.name_ban}")
    private String queueBan;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key_pwd}")
    private String routingKeyPwd;
    @Value("${rabbitmq.routing.key_ban}")
    private String routingKeyBan;

    @Bean
    public Queue queuePwd(){
        return new Queue(queuePWD);
    }

    @Bean
    public Queue queueBanUser(){
        return new Queue(queueBan);
    }

    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(exchange);
    }

    @Bean
    public Binding bindingChangePwd(){
        return BindingBuilder
                .bind(queuePwd())
                .to(exchange())
                .with(routingKeyPwd);
    }

    @Bean
    public Binding bindingBanUser(){
        return BindingBuilder
                .bind(queueBanUser())
                .to(exchange())
                .with(routingKeyBan);
    }
}
