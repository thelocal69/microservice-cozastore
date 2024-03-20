package com.cozastore.blogservice.dto;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BlogDTO {
    private String id;
    private String title;
    private String imageUrl;
    private String content;
    private String userId;
    private int status;
    private String createdBy;
    private Timestamp createdDate;
}
