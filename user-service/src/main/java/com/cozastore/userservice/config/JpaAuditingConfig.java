package com.cozastore.userservice.config;

import lombok.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;

@Configuration
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class JpaAuditingConfig {
    @Bean
    public AuditorAware<String> auditorProvider(){
        //tracking date time
        return new AuditorAwareImpl();
    }

    public static class AuditorAwareImpl implements AuditorAware<String> {

        //use nested class
        @Override
        public @NonNull Optional<String> getCurrentAuditor() {
//            //tracking user
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            if (authentication == null){
//                return Optional.of("Anonymous");
//            }
//            if (!authentication.isAuthenticated()){
//                return Optional.empty();
//            }
//            String username = authentication.getName();
//            return Optional.of(username);
            return Optional.empty();
        }
    }
}
