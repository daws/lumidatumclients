package com.lumidatum.client;

import com.fasterxml.jackson.annotation.JsonProperty;


public class ProductRecommendation {

    private long product_id;
    private String product_description;

    public ProductRecommendation() {
    }

    public ProductRecommendation(long productId) {
        this.product_id = productId;
    }

    public ProductRecommendation(String productDescription) {
        this.product_description = productDescription;
    }

    public ProductRecommendation(long productId, String productDescription) {
        this.product_id = productId;
        this.product_description = productDescription;
    }

    @JsonProperty("product_id")
    public long getProductId() {
        return this.product_id;
    }
    @JsonProperty("product_id")
    public void setProductId(long productId) {
        this.product_id = productId;
    }

    @JsonProperty("product_description")
    public String getProductDescription() {
        return this.product_description;
    }
    @JsonProperty("product_description")
    public void setProductDescription(String productDescription) {
        this.product_description = productDescription;
    }
}