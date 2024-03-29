package com.cozastore.productservice.config;

import com.cozastore.productservice.feign.AuthClient;
import feign.Feign;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
public class AppConfig {

    @Value("${gate-way.host}")
    private String host;
    @Value("${gate-way.port}")
    private String port;

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
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
