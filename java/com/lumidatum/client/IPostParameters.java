package com.lumidatum.client;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;


public interface IPostParameters {

    public String serializeToJson() throws JsonProcessingException;
}