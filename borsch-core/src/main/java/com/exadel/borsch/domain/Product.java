package com.exadel.borsch.domain;

/**
 * Created by srw on 8/1/13.
 */
public class Product {
    private Long productId;
    private String name;
    private String desription;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesription() {
        return desription;
    }

    public void setDesription(String desription) {
        this.desription = desription;
    }
}
