package com.jd.jdbc.test;
import java.io.Serializable;
import java.util.Date;

public class Order implements Serializable {
    private Long id;
    private Long orderId;
    private Long userId;
    private Double price;
    private Date createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", userId=" + userId +
                ", price=" + price +
                ", createDate=" + createDate +
                '}';
    }
}
