package com.torrenueva.alier.model.dao.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name="tb_product")
@Data
public class ProductEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int productId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "photo")
	private String[] photo;
	
	@Column(name="price")
	private int price;
	
	@Column(name="description")
	private String description;
	
	@Column(name="specification")
	private String specification;
	
	@Column(name="discount")
	private String discount;
	
	@Column(name="delete_flag")
	private boolean deleteFlag;
}
