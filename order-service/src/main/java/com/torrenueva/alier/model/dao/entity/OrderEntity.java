package com.torrenueva.alier.model.dao.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_order")
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private int orderId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    /**
     * JSONB column
     * Structure:
     * [
     *   { "product_id": 1, "quantity": 2, "sub_total": 500.00 }
     * ]
     */
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "items", columnDefinition = "jsonb", nullable = false)
    private String items;

    @Column(name = "total_price", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalPrice;

    @Column(name = "status", nullable = false, length = 250)
    private String status;

    @Column(name = "date_order", nullable = false)
    private LocalDateTime dateOrder;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @Column(name = "delete_flag", nullable = false)
    private Boolean deleteFlag;
}
