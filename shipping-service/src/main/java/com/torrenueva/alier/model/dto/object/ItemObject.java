package com.torrenueva.alier.model.dto.object;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class ItemObject {
    private int productId;
    private int quantity;
    private BigDecimal subTotal;
}
