package com.vrk.app.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductDTO implements Serializable {
    private Long id;
    private String name;
    private Integer qty;
    private BigDecimal price;
}
