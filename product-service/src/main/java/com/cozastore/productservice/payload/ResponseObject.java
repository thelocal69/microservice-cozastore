package com.cozastore.productservice.payload;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseObject {
    private Object data;
}
