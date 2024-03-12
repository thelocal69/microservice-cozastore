package com.cozastore.userservice.payload;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOutput {
    private int page;
    private int totalItem;
    private int totalPage;
    private Object data;
}
