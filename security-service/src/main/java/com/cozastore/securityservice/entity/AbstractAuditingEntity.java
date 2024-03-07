package com.cozastore.securityservice.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;

import java.sql.Timestamp;

@Getter
@Setter
public class AbstractAuditingEntity {
    @CreatedBy
    @Field(name = "created_by")
    private String createdBy;

    @CreatedDate
    @Field(name = "created_date")
    private Timestamp createdDate;

    @LastModifiedBy
    @Field(name = "last_modified_by")
    private String lastModifiedBy;

    @LastModifiedDate
    @Field(name = "last_modified_date")
    private Timestamp lastModifiedDate;
}
