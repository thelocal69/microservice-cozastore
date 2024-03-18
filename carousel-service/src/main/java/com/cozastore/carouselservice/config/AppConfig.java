package com.cozastore.carouselservice.config;

import com.cozastore.carouselservice.feign.AuthClient;
import com.cozastore.carouselservice.feign.ICategoryClient;
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
    public AuthClient authClient(){
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .target(AuthClient.class,
                        "%s:%s/".formatted(host, port));
    }

    @Bean
    public ICategoryClient getCategoryClient() {
        return Feign.builder().client(new OkHttpClient()).encoder(new GsonEncoder())
                .decoder(new GsonDecoder()).target(ICategoryClient.class,
                        "%s:%s/".formatted(host, port));
    }
}
