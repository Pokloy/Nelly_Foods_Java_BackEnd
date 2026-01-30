package com.torrenueva.alier.model.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torrenueva.alier.model.dao.ShippingDao;
import com.torrenueva.alier.model.dao.entity.ShippingEntity;
import com.torrenueva.alier.model.dto.ShippingDto;
import com.torrenueva.alier.model.service.ShippingService;

@Service
public class ShippingServiceImpl implements ShippingService {
	@Autowired
	private ShippingDao shippingDao;
	
	@Override
	public List<ShippingDto> getAllShipping(){
		List<ShippingEntity> entityList = shippingDao.findAll();
		
	    if (entityList == null || entityList.isEmpty()) {
	        return new ArrayList<>();
	    }
	    
	    return entityList.stream()
	    		.map(entity -> {
	    			ShippingDto dto = new ShippingDto();
	    			dto.setShippingId(entity.getShippingId());
	    			dto.setOrderId(entity.getOrderId());
	    			dto.setUserId(entity.getUserId());
	    			dto.setTrackingNumber(entity.getTrackingNumber());
	    			dto.setStatus(entity.getStatus());
	    			dto.setDeleteflg(entity.isDeleteflg());
	    			
	    			// Direct assignment! 
	                dto.setDeliveryAddress(entity.getDeliverAddress());
	                
	    			return dto;
	    		}).toList();
	}
	
	@Override
	public String addShipping(ShippingDto shippingDto) {
		ShippingEntity entity = new ShippingEntity();
		
		if(shippingDto == null) {
			return "No data inserted";
		}
		
		entity.setOrderId(shippingDto.getOrderId());
		entity.setUserId(shippingDto.getUserId());
		entity.setTrackingNumber(shippingDto.getTrackingNumber());
		entity.setStatus(shippingDto.getStatus());
		entity.setDeleteflg(false);
		entity.setDeliverAddress(shippingDto.getDeliveryAddress());
		shippingDao.saveAndFlush(entity);
		return "Order Id: " +shippingDto.getOrderId()+ " has been in shipped";
	}
	
	@Override
	public List<ShippingDto> allShipByUserId(int userId){
		List<ShippingEntity> entities = shippingDao.getAllShipByUserId(userId);
		System.out.println(entities);
		return entities.stream()
				.map(entity -> {
			ShippingDto successDto = new ShippingDto();
			successDto.setShippingId(entity.getShippingId());
			successDto.setOrderId(entity.getOrderId());
			successDto.setUserId(entity.getUserId());
			successDto.setTrackingNumber(entity.getTrackingNumber());
			successDto.setStatus(entity.getStatus());
			successDto.setDeleteflg(entity.isDeleteflg());
			successDto.setDeliveryAddress(entity.getDeliverAddress());
			return successDto;
		}).toList();
	}
}
