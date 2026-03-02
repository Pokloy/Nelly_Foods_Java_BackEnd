package com.torrenueva.alier.model.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.torrenueva.alier.model.dto.object.ItemObject;

import lombok.Data;

@Data
public class OrderDto {
	
	    private int orderId;
	    
	    private int userId;

	   private List<ItemObject> items;

	    private BigDecimal totalPrice;

	    private String status;

	    private LocalDateTime dateOrder;

	    private LocalDateTime updateDate;

	    private Boolean deleteFlag;
}
