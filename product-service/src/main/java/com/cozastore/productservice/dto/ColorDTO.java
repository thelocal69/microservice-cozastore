package com.cozastore.productservice.dto;

import com.cozastore.productservice.model.AbstractAuditingModel;
import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ColorDTO{
    private String id;
    private String name;
}
