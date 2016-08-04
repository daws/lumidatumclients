package com.lumidatum.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.lumidatum.client.IPostParameters;
import com.lumidatum.client.PostParametersBase;


public class ProductPostParameters extends PostParametersBase implements IPostParameters {

    public ProductPostParameters() {
    }

    public ProductPostParameters(long customerId, long orderId, List<Long> includedProductIds, List<Long> excludedProductIds, int numberOfRecommendations) {
        super(customerId, orderId, includedProductIds, excludedProductIds, numberOfRecommendations);
    }

    @JsonProperty("customer_id")
    public long getCustomerId() {
        return this.customer_id;
    }
    @JsonProperty("customer_id")
    public void setCustomerId(long customerId) {
        this.customer_id = customerId;
    }

    @JsonProperty("order_id")
    public long getOrderId() {
        return this.order_id;
    }
    @JsonProperty("order_id")
    public void setOrderId(long orderId) {
        this.order_id = orderId;
    }

    @JsonProperty("inc_prod_ids")
    public List<Long> getIncludedProductIds() {
        return this.inc_prod_ids;
    }
    @JsonProperty("inc_prod_ids")
    public void setIncludedProductIds(List<Long> includedProductIds) {
        this.inc_prod_ids = includedProductIds;
    }

    @JsonProperty("exc_prod_ids")
    public List<Long> getExcludedProductIds() {
        return this.exc_prod_ids;
    }
    @JsonProperty("exc_prod_ids")
    public void setExcludedProductIds(List<Long> excludedProductIds) {
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