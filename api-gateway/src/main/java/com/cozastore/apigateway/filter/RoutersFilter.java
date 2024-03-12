package com.cozastore.apigateway.filter;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RoutersFilter {
    public static final List<String> openAPIEndpoints = List.of(
            "/api/account/register",
            "/api/account/login",
            "/api/account/login_admin",
            "/api/account/logout",
            "/api/account/validate",
            "/api/account/verify_email",
            "/api/account/forgot_password",
            "/api/account/set_password",
            "/api/account/resend_active",
            "/api/account/gen_access",
            "/api/product",
            "/api/media/{folderName}/{fileName}",
            "/eureka"
    );

    public Predicate<ServerHttpRequest> isSecured =
            serverHttpRequest -> openAPIEndpoints.stream()
                    .noneMatch(uri -> serverHttpRequest.getURI().getPath().contains(uri));
}
