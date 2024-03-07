package com.cozastore.carouselservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarouselDTO {
    private String id;
    private String imageUrl;
    private String title;
    private String content;
    private String categoryId;
    private int status;
}
