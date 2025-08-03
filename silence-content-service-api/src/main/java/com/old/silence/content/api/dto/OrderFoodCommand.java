package com.old.silence.content.api.dto;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author moryzang
 * @Description
 */
public class OrderFoodCommand {

    private BigInteger id;
    private BigInteger orderId;
    private BigInteger foodId;
    private Integer number;
    private BigDecimal price;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public BigInteger getOrderId() {
        return orderId;
    }

    public void setOrderId(BigInteger orderId) {
        this.orderId = orderId;
    }

    public BigInteger getFoodId() {
        return foodId;
    }

    public void setFoodId(BigInteger foodId) {
        this.foodId = foodId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
