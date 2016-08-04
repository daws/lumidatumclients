package com.lumidatum;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.lumidatum.client.AuctionPostParameters;
import com.lumidatum.client.AuctionResponse;
import com.lumidatum.client.ProductPostParameters;
import com.lumidatum.client.ProductResponse;


public interface IClient {

    public ProductResponse postPredict(ProductPostParameters parameters) throws IOException, JsonProcessingException;
    public ProductResponse postPredict(long modelId, ProductPostParameters parameters) throws IOException, JsonProcessingException;
    public AuctionResponse postPredict(AuctionPostParameters parameters) throws IOException, JsonProcessingException;
    public AuctionResponse postPredict(long modelId, AuctionPostParameters parameters) throws IOException, JsonProcessingException;
}