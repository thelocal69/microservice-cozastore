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
@Document(collection = "blogs")
public class BlogEntity extends AbstractAuditingEntity{
    @Id
    @Indexed(unique = true)
    private String id;
    @Field(name = "title")
    private String title;
    @Field(name = "image_url")
    private String imageUrl;
    @Field(name = "content")
    private String content;
    @Field(name = "user_id")
    private Long userId;
    @Field(name = "status")
    private int status;
}
