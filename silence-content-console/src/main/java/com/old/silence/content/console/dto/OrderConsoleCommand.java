package com.old.silence.content.console.dto;

import com.old.silence.content.domain.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author moryzang
 */
public class OrderConsoleCommand {

    private String userId;

    private Boolean taken;

    private Boolean paid;

    private String comment;

    private OrderStatus status;

    private BigDecimal price;

    private BigDecimal promotion;

    private Integer number;

    private List<OrderFoodConsoleCommand> orderFoods;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Boolean getTaken() {
        return taken;
    }

    public void setTaken(Boolean taken) {
        this.taken = taken;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPromotion() {
        return promotion;
    }

    public void setPromotion(BigDecimal promotion) {
        this.promotion = promotion;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public List<OrderFoodConsoleCommand> getOrderFoods() {
        return orderFoods;
    }

    public void setOrderFoods(List<OrderFoodConsoleCommand> orderFoods) {
        this.orderFoods = orderFoods;
    }
}
