package com.cozastore.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RoutersFilter {
    public static final List<String> openAPIEndpoints = List.of(
            "/api/account/register",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            serverHttpRequest -> openAPIEndpoints.stream()
                    .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));
}
