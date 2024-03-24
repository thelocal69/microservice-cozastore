package com.cozastore.blogservice.dto;

import lombok.*;

import java.sql.Timestamp;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String id;
    private String content;
    private String blogId;
    private String userId;
    private int status;
    private Timestamp createdDate;
}
