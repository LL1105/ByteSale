package com.sale.dto;

import java.math.BigDecimal;
import java.util.Date;

public class PromotionDTO {
    private Long id; // 促销ID
    private Long productId; // 商品ID
    private String promotionType; // 促销类型（如折扣、优惠券）
    private BigDecimal discount; // 折扣金额
    private Date startTime; // 促销开始时间
    private Date endTime; // 促销结束时间

    // Getters and Setters
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
}
