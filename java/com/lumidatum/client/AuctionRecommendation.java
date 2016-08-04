package com.lumidatum.client;

import com.fasterxml.jackson.annotation.JsonProperty;


public class AuctionRecommendation {

    private long product_id;
    private String product_description;

    public AuctionRecommendation() {
    }

    public AuctionRecommendation(long itemId) {
        this.product_id = itemId;
    }

    public AuctionRecommendation(String itemDescription) {
        this.product_description = itemDescription;
    }

    public AuctionRecommendation(long itemId, String itemDescription) {
        this.product_id = itemId;
        this.product_description = itemDescription;
    }

    @JsonProperty("product_id")
    public long getItemId() {
        return this.product_id;
    }
    @JsonProperty("product_id")
    public void setItemId(long itemId) {
        this.product_id = itemId;
    }

    @JsonProperty("product_description")
    public String getItemDescription() {
        return this.product_description;
    }
    @JsonProperty("product_description")
    public void setItemDescription(String itemDescription) {
        this.product_description = itemDescription;
    }
}