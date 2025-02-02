package com.sale.model;


public class Inventory {
    private Long id; // 库存ID
    private Long productId; // 商品ID
    private Integer stock; // 库存数量

    // 无参构造函数
    public Inventory() {
    }

    // 带参构造函数
    public Inventory(Long id, Long productId, Integer stock) {
        this.id = id;
        this.productId = productId;
        this.stock = stock;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", productId=" + productId +
                ", stock=" + stock +
                '}';
    }
}

