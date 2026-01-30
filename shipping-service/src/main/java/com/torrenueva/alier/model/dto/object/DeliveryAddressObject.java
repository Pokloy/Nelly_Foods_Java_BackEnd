package com.torrenueva.alier.model.dto.object;

import lombok.Data;

@Data
public class DeliveryAddressObject {
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String address;
	private String city;
	private String state;
	private String zipCode;
}
