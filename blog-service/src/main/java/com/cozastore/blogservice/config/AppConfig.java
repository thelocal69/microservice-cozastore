package com.cozastore.blogservice.config;

import com.cozastore.blogservice.feign.AuthClient;
import com.cozastore.blogservice.feign.IProductClient;
import com.cozastore.blogservice.feign.IUserClient;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${gate-way.host}")
    private String host;
    @Value("${gate-way.port}")
    private String port;

    @Bean
    public IUserClient userClient(){
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(IUserClient.class, "%s:%s/".formatted(host, port));
    }

    @Bean
    public IProductClient productClient(){
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(IProductClient.class, "%s:%s/".formatted(host, port));
    }

    @Bean
    public AuthClient authClient(){
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(AuthClient.class,
                        "%s:%s/".formatted(host, port));
    }
}
