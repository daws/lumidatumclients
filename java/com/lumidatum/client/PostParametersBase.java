package com.lumidatum.client;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class PostParametersBase {

    protected long customer_id;
    protected long order_id;
    protected List<Long> inc_prod_ids;
    protected List<Long> exc_prod_ids;
    protected int num_of_recs;

    public PostParametersBase() {
    }

    public PostParametersBase(long customerId, List<Long> includedProductIds, List<Long> excludedProductIds, int numberOfRecommendations) {
        this.customer_id = customerId;
        this.inc_prod_ids = includedProductIds;
        this.exc_prod_ids = excludedProductIds;
        this.num_of_recs = numberOfRecommendations;
    }

    public PostParametersBase(long customerId, long orderId, List<Long> includedProductIds, List<Long> excludedProductIds, int numberOfRecommendations) {
        this.customer_id = customerId;
        this.order_id = orderId;
        this.inc_prod_ids = includedProductIds;
        this.exc_prod_ids = excludedProductIds;
        this.num_of_recs = numberOfRecommendations;
    }

    public String serializeToJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString;

        try {
            jsonString = mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            System.out.println("Serialization failure");
            throw e;
        }

        return jsonString;
    }
}