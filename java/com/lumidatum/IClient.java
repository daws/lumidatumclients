package com.lumidatum;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.lumidatum.client.AuctionPostParameters;
import com.lumidatum.client.AuctionResponse;
import com.lumidatum.client.ProductPostParameters;
import com.lumidatum.client.ProductResponse;
import com.lumidatum.client.SendFileResponse;


public interface IClient {

    public ProductResponse postPredict(ProductPostParameters parameters) throws IOException, JsonProcessingException;
    public ProductResponse postPredict(long modelId, ProductPostParameters parameters) throws IOException, JsonProcessingException;
    public AuctionResponse postPredict(AuctionPostParameters parameters) throws IOException, JsonProcessingException;
    public AuctionResponse postPredict(long modelId, AuctionPostParameters parameters) throws IOException, JsonProcessingException;

    // public SendFileResponse sendUserProfiles(String inputFilePath) throws IOException, JsonProcessingException;
    // public SendFileResponse sendUserProfiles(File inputFile) throws IOException, JsonProcessingException;
    // public SendFileResponse sendItemProfiles(String intpuFilePath) throws IOException, JsonProcessingException;
    // public SendFileResponse sendItemProfiles(File inputFile) throws IOException, JsonProcessingException;
    public SendFileResponse sendTransactionsFile(String inputFilePath) throws IOException, JsonProcessingException;
    public SendFileResponse sendTransactionsFile(File inputFile) throws IOException, JsonProcessingException;
}