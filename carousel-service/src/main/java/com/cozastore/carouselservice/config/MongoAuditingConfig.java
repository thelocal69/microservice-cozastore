package com.cozastore.carouselservice.config;

import lombok.NonNull;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Configuration
@EnableMongoRepositories(basePackages = "com.cozastore.carouselservice.repository")
@EnableMongoAuditing(auditorAwareRef = "auditorProvider")
public class MongoAuditingConfig extends AbstractMongoClientConfiguration {

    @Bean
    public AuditorAware<String> auditorProvider(){
        //tracking date time
        return new AuditorAwareImpl();
    }

    @NotNull
    @Override
    protected String getDatabaseName() {
        return "cozastore-carousel-service";
    }

    @NotNull
    @Override
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new TimeStampReadConverter());
        converters.add(new TimeStampWriteConverter());
        return new MongoCustomConversions(converters);
    }

    public static class TimeStampReadConverter implements Converter<Date, Timestamp>{

        @Override
        public Timestamp convert(@NotNull Date date) {
            return new Timestamp(date.getTime());
        }
    }

    public static class TimeStampWriteConverter implements Converter<Timestamp, Date>{

        @Override
        public Date convert(@NotNull Timestamp timestamp) {
            return Date.from(timestamp.toInstant());
        }
    }

    public static class AuditorAwareImpl implements AuditorAware<String> {

        //use nested class
        @Override
        public @NonNull Optional<String> getCurrentAuditor() {
            //tracking user
            ///Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String username = authentication.getName();
//            String nickName = username;
//            if (username.contains("@")){
//                int index = username.indexOf("@");
//                nickName = username.substring(0 ,index);
//            }
//            if (!authentication.isAuthenticated()){
//                return Optional.empty();
//            }
//            return Optional.of(nickName);
            return Optional.empty();
        }
    }
}
