package com.torrenueva.alier.model.dto;

import com.torrenueva.alier.model.dto.object.DeliveryAddressObject;

import lombok.Data;

@Data
public class ShippingDto {
	private int shippingId;
	private int orderId;
	private int userId;
	private DeliveryAddressObject deliveryAddress;
	private String trackingNumber;
	private String status;
	private boolean deleteflg;
	private String msgResult;
}
