package com.lumidatum.client;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.lumidatum.client.AuctionRecommendation;


public class AuctionResponse {

    private long model_id;
    private long order_id;
    private long customer_id;
    private Date created_at;
    private List<AuctionRecommendation> recommendations;

    public AuctionResponse() {
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
    public long getPostingId() {
        return this.order_id;
    }
    @JsonProperty("order_id")
    public void setPostingId(long postingId) {
        this.order_id = postingId;
    }

    @JsonProperty("customer_id")
    public long getBidderId() {
        return this.customer_id;
    }
    @JsonProperty("customer_id")
    public void setBidderId(long bidderId) {
        this.customer_id = bidderId;
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
    public List<AuctionRecommendation> getRecommendations() {
        return this.recommendations;
    }
    @JsonProperty("recommendations")
    public void setRecommendations(List<AuctionRecommendation> recommendations) {
        this.recommendations = recommendations;
    }
}