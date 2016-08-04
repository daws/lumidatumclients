package com.lumidatum.client;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.lumidatum.client.ProductRecommendation;


public class ProductResponse {

    private long model_id;
    private long order_id;
    private long customer_id;
    private Date created_at;
    private List<ProductRecommendation> recommendations;

    public ProductResponse() {
    }

    @JsonProperty("model_id")
    public long getModelId() {
        return this.model_id;
    }
    @JsonProperty("model_id")
    public void setModelId(long modelId) {
        this.model_id = modelId;
    }

    @JsonProperty("order_id")
    public long getOrderId() {
        return this.order_id;
    }
    @JsonProperty("order_id")
    public void setOrderId(long orderId) {
        this.order_id = orderId;
    }

    @JsonProperty("customer_id")
    public long getCustomerId() {
        return this.customer_id;
    }
    @JsonProperty("customer_id")
    public void setCustomerId(long customerId) {
        this.customer_id = customerId;
    }

    @JsonProperty("created_at")
    public Date getCreatedAt() {
        return this.created_at;
    }
    @JsonProperty("created_at")
    public void setCreatedAt(Date createdAt) {
        this.created_at = createdAt;
    }

    @JsonProperty("recommendations")
    public List<ProductRecommendation> getRecommendations() {
        return this.recommendations;
    }
    @JsonProperty("recommendations")
    public void setRecommendations(List<ProductRecommendation> recommendations) {
        this.recommendations = recommendations;
    }
}