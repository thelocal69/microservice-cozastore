package com.cozastore.apigateway.filter;

import com.cozastore.apigateway.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {

    private final RoutersFilter routersFilter;
    private final JwtUtils jwtUtils;

    @Autowired
    public AuthenticationFilter(RoutersFilter routersFilter, JwtUtils jwtUtils){
        super(Config.class);
        this.routersFilter = routersFilter;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (((exchange, chain) -> {
            if (routersFilter.isSecured.test(exchange.getRequest())){
                if (!exchange.getRequest().getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                    throw new RuntimeException("Missing Authorization headers !");
                }
                String authHeaders = Objects.requireNonNull(exchange.getRequest().getHeaders().get(HttpHeaders.AUTHORIZATION)).get(0);
                if (authHeaders != null && authHeaders.startsWith("Bearer ")){
                    authHeaders = authHeaders.substring(7);
                }
                try {
                    this.jwtUtils.parserToken(authHeaders);
                }catch (Exception e){
                    throw new RuntimeException("Un authorization application !");
                }
            }
            return chain.filter(exchange);
        }));
    }

    public static class Config{

    }
}
