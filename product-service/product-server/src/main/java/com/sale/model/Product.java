package com.sale.model;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "products") // Elasticsearch 索引名称
public class Product {

    @Id
    private Long id; // 商品ID

    @Field(type = FieldType.Text, analyzer = "ik_max_word") // 使用 IK 分词器
    private String name; // 商品名称

    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String description; // 商品描述

    @Field(type = FieldType.Double)
    private BigDecimal price; // 商品价格

    @Field(type = FieldType.Keyword)
    private String imageUrl; // 商品图片URL

    @Field(type = FieldType.Keyword)
    private Long categoryId; // 分类ID

    @Field(type = FieldType.Keyword)
    private Long brandId; // 品牌ID

    @Field(type = FieldType.Date)
    private Date createTime; // 创建时间

    @Field(type = FieldType.Date)
    private Date updateTime; // 更新时间

    public Product() {
    }

    public Product(Long id, String name, String description, BigDecimal price, String imageUrl,
                   Long categoryId, Long brandId, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageUrl = imageUrl;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    // Getter 和 Setter 方法

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getBrandId() {
        return brandId;
    }

    public void setBrandId(Long brandId) {
        this.brandId = brandId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", categoryId=" + categoryId +
                ", brandId=" + brandId +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
