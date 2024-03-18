package com.cozastore.blogservice.dto;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TagDTO {
    private String id;
    private String name;
    private String productId;
    private String blogId;
    private int status;
}
