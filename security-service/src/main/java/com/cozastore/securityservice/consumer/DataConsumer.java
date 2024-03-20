package com.cozastore.securityservice.consumer;

import com.cozastore.securityservice.dto.BanUserDTO;
import com.cozastore.securityservice.dto.ChangePasswordDTO;
import com.cozastore.securityservice.entity.UserEntity;
import com.cozastore.securityservice.exception.AccountException;
import com.cozastore.securityservice.exception.NotFoundException;
import com.cozastore.securityservice.repository.IUserRepository;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class DataConsumer {

    private final IUserRepository userRepository;
    private final Gson gson;
    private final PasswordEncoder passwordEncoder;

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${rabbitmq.user.queue.name_changepwd}", durable = "true"),
                    exchange = @Exchange(value = "${rabbitmq.user.exchange.name}"),
                    key = "${rabbitmq.user.routing.key_pwd}"
            )
    )
    public void processQueueChangePassword(String message){
        try{
            ChangePasswordDTO changePasswordDTO = gson.fromJson(
                    message, ChangePasswordDTO.class
            );
            UserEntity user = userRepository.findByEmail(changePasswordDTO.getEmail());
            if (user == null){
                log.info("User is not exist !");
                throw new NotFoundException("User is not exist !");
            }
            if (!(passwordEncoder.matches(changePasswordDTO.getCurrentPassword(), user.getPassword()))){
                log.info("Wrong password !");
                throw new AccountException("Wrong password !");
            }
            if (!(changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword()))){
                log.info("Password won't match !");
                throw new AccountException("Password won't match !");
            }
            try {
                String password = passwordEncoder.encode(changePasswordDTO.getConfirmPassword());
                user.setPassword(password);
                this.userRepository.save(user);
            }catch (Exception ex){
                log.info(ex.getMessage());
            }
        }catch (AccountException exception){
            log.info(String.format("Exception error %s", exception.getMessage()));
        }
    }

    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "${rabbitmq.user.queue.name_ban}", durable = "true"),
                    exchange = @Exchange(value = "${rabbitmq.user.exchange.name}"),
                    key = "${rabbitmq.user.routing.key_ban}"
            )
    )
    public void processQueueBanUser(String message){
        try {
            BanUserDTO banUserDTO = gson.fromJson(
                    message, BanUserDTO.class
            );
            UserEntity user = userRepository.findOneById(banUserDTO.getId());
            if (user == null){
                log.info("User is not exist !");
                throw new NotFoundException("User is not exist !");
            }
            user.setStatus(banUserDTO.getStatus());
            this.userRepository.save(user);
        }catch (AccountException exception){
            log.info(String.format("Exception error %s", exception.getMessage()));
        }
    }
}
