package com.vrk.app.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private Integer qty;
    private BigDecimal price;
}
