package com.cozastore.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class securityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity serverHttpSecurity) {
        serverHttpSecurity.authorizeExchange(
                        exchange -> exchange.pathMatchers("/**").permitAll().anyExchange().authenticated())
                .csrf(ServerHttpSecurity.CsrfSpec::disable).cors(Customizer.withDefaults());
        return serverHttpSecurity.build();
    }
}
