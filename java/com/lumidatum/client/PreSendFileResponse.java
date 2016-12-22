package com.lumidatum.client;

import java.net.URL;
import java.util.HashMap;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.lumidatum.client.PreSendFileResponseFields;


public class PreSendFileResponse {

    private String fileStorageHost;
    private URL url;
    private PreSendFileResponseFields fields;

    public PreSendFileResponse() {
    }

    @JsonProperty("Storage-Host")
    public String getFileStorageHost() {

        return this.fileStorageHost;
    }

    @JsonProperty("Storage-Host")
    public void setFileStorageHost(String fileStorageHost) {
        this.fileStorageHost = fileStorageHost;
    }

    @JsonProperty("url")
    public URL getUploadUrl() {

        return this.url;
    }
    @JsonProperty("url")
    public void setUploadUrl(String upload_url_string) throws IOException {
        this.url = new URL(upload_url_string);
    }

    // Other fields...
    @JsonProperty("fields")
    public PreSendFileResponseFields getFields() {

        return this.fields;
    }

    @JsonProperty("fields")
    public void setFields(PreSendFileResponseFields fields) {
        this.fields = fields;
    }
}