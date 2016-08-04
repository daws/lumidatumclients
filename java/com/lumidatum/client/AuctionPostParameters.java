package com.lumidatum.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.lumidatum.client.IPostParameters;
import com.lumidatum.client.PostParametersBase;


public class AuctionPostParameters extends PostParametersBase implements IPostParameters {

    public AuctionPostParameters() {
    }

    public AuctionPostParameters(long bidderId, List<Long> includedItemIds, List<Long> excludedItemIds, int numberOfRecommendations) {
        super(bidderId, includedItemIds, excludedItemIds, numberOfRecommendations);
    }

    @JsonProperty("customer_id")
    public long getBidderId() {
        return this.customer_id;
    }
    @JsonProperty("customer_id")
    public void setBidderId(long bidderId) {
        this.customer_id = bidderId;
    }

    @JsonProperty("order_id")
    public long getPostingId() {
        return this.order_id;
    }
    @JsonProperty("order_id")
    public void setPostingId(long postingId) {
        this.order_id = postingId;
    }

    @JsonProperty("inc_prod_ids")
    public List<Long> getIncludedItemIds() {
        return this.inc_prod_ids;
    }
    @JsonProperty("inc_prod_ids")
    public void setIncludedItemIds(List<Long> includedProductIds) {
        this.inc_prod_ids = includedProductIds;
    }

    @JsonProperty("excludedProductIds")
    public List<Long> getExcludedItemIds() {
        return this.exc_prod_ids;
    }
    @JsonProperty("excludedProductIds")
    public void setExcludedItemIds(List<Long> excludedProductIds) {
        this.exc_prod_ids = excludedProductIds;
    }

    @JsonProperty("num_of_recs")
    public int getNumberOfRecommendations() {
        return this.num_of_recs;
    }
    @JsonProperty("num_of_recs")
    public void setNumberOfRecommendations(int numberOfRecommendations) {
        this.num_of_recs = numberOfRecommendations;
    }
}