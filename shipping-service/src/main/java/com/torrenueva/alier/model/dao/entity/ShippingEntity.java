package com.torrenueva.alier.model.dao.entity;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import com.torrenueva.alier.model.dto.object.DeliveryAddressObject;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_shipping")
public class ShippingEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipping_id")
	private int shippingId;
	
	@Column(name = "order_id")
	private int orderId;
	
	@Column(name = "user_id", nullable = false)
	private int userId;
	
    /**
     * JSONB column
     * Structure:
     *   { 
     *   "first_name": "sample first name", 
     *   "last_name": "sample last name", 
     *   "email": "sample@gmail.com", 
     *   "phone": "09491713389", 
     *   "address": "Barangay Pooc, Sitio Kanipaan",
     *   "city": "Talisay City",
     *   "state": "Cebu",
     *   "zip_code": 4065
     *   }
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "deliver_address", columnDefinition = "jsonb", nullable = false)
    private DeliveryAddressObject deliverAddress;
	
    @Column(name = "tracking_number")
    private String trackingNumber;
    
    @Column(name = "status")
	private String status;
	
    @Column(name = "delete_flag")
	private boolean deleteflg;
}
