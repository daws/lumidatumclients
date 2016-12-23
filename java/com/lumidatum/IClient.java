package com.lumidatum;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.lumidatum.client.AuctionPostParameters;
import com.lumidatum.client.AuctionResponse;
import com.lumidatum.client.ProductPostParameters;
import com.lumidatum.client.ProductResponse;
import com.lumidatum.client.SendDataResponse;


public interface IClient {

    public ProductResponse postPredict(ProductPostParameters parameters) throws IOException, JsonProcessingException;
    public ProductResponse postPredict(long modelId, ProductPostParameters parameters) throws IOException, JsonProcessingException;
    public AuctionResponse postPredict(AuctionPostParameters parameters) throws IOException, JsonProcessingException;
    public AuctionResponse postPredict(long modelId, AuctionPostParameters parameters) throws IOException, JsonProcessingException;

    public SendDataResponse sendUserProfilesString(String data) throws IOException,JsonProcessingException;
    public SendDataResponse sendUserProfilesFile(String inputFilePath) throws IOException, JsonProcessingException;
    public SendDataResponse sendUserProfilesFile(File inputFile) throws IOException, JsonProcessingException;
    public SendDataResponse sendItemProfilesString(String data) throws IOException,JsonProcessingException;
    public SendDataResponse sendItemProfilesFile(String intpuFilePath) throws IOException, JsonProcessingException;
    public SendDataResponse sendItemProfilesFile(File inputFile) throws IOException, JsonProcessingException;
    public SendDataResponse sendTransactionsString(String data) throws IOException,JsonProcessingException;
    public SendDataResponse sendTransactionsFile(String inputFilePath) throws IOException, JsonProcessingException;
    public SendDataResponse sendTransactionsFile(File inputFile) throws IOException, JsonProcessingException;
}