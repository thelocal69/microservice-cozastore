package com.cozastore.productservice.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
