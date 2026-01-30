package com.torrenueva.alier.model.service;

import java.util.List;

import com.torrenueva.alier.model.dto.ShippingDto;

public interface ShippingService {
	public List<ShippingDto> getAllShipping();
	public String addShipping(ShippingDto shippingDto);
	public List<ShippingDto> allShipByUserId(int userId);
}
