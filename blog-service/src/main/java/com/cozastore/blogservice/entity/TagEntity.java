package com.cozastore.blogservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "tags")
public class TagEntity extends AbstractAuditingEntity{
    @Id
    @Indexed(unique = true)
    private String id;
    @Field(name = "name")
    private String name;
    @Field(name = "blog_id")
    private BlogEntity blogId;
    @Field(name = "product_id")
    private String productId;
    @Field(name = "status")
    private int status;
}
