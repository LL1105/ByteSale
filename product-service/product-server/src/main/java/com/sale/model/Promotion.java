package com.sale.model;

import java.math.BigDecimal;
import java.util.Date;

public class Promotion {
    private Long id; // 促销ID
    private Long productId; // 商品ID
    private String promotionType; // 促销类型
    private BigDecimal discount; // 折扣金额
    private Date startTime; // 开始时间
    private Date endTime; // 结束时间

    // 无参构造函数
    public Promotion() {
    }

    // 带参构造函数
    public Promotion(Long id, Long productId, String promotionType, BigDecimal discount, Date startTime, Date endTime) {
        this.id = id;
        this.productId = productId;
        this.promotionType = promotionType;
        this.discount = discount;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getter 和 Setter 方法

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getPromotionType() {
        return promotionType;
    }

    public void setPromotionType(String promotionType) {
        this.promotionType = promotionType;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Promotion{" +
                "id=" + id +
                ", productId=" + productId +
                ", promotionType='" + promotionType + '\'' +
                ", discount=" + discount +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
