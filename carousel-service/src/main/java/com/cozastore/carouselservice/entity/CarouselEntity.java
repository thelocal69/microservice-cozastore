package com.cozastore.carouselservice.entity;

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
@Document(collection = "carousel")
public class CarouselEntity extends AbstractAuditingEntity{

    @Id
    @Indexed(unique = true)
    private String id;
    @Field(name = "image_url")
    private String imageUrl;
    @Field(name = "title")
    private String title;
    @Field("content")
    private String content;
    @Field(name = "category_id")
    private String categoryId;
    @Field(name = "status")
    private int status;
}
