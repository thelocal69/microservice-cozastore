package com.cozastore.cartservice.payload;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseOutput {
    private int page;
    private int totalPage;
    private int totalItem;
    private Object data;
}
