package com.cozastore.productservice.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableMongoRepositories(basePackages = "com.cozastore.productservice.repository")
@EnableMongoAuditing()
public class MongoAuditingConfig extends AbstractMongoClientConfiguration {

    @NotNull
    @Override
    protected String getDatabaseName() {
        return "cozastore-product-service";
    }

    @NotNull
    @Override
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new TimeStampReadConverter());
        converters.add(new TimeStampWriteConverter());
        return new MongoCustomConversions(converters);
    }

    public static class TimeStampReadConverter implements Converter<Date, Timestamp> {

        @Override
        public Timestamp convert(@NotNull Date date) {
            return new Timestamp(date.getTime());
        }
    }

    public static class TimeStampWriteConverter implements Converter<Timestamp, Date> {

        @Override
        public Date convert(@NotNull Timestamp timestamp) {
            return Date.from(timestamp.toInstant());
        }
    }

}
