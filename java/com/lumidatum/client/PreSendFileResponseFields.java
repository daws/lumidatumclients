package com.lumidatum.client;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PreSendFileResponseFields {

    private String awsAccessKeyId;
    private String acl;
    private String key;
    private String signature;
    private String policy;
    private String contentType;

    public PreSendFileResponseFields() {
    }

    @JsonProperty("AWSAccessKeyId")
    public String getAWSAccessKeyId() {

        return this.awsAccessKeyId;
    }

    @JsonProperty("AWSAccessKeyId")
    public void setAWSAccessKeyID(String awsAccessKeyId) {
        this.awsAccessKeyId = awsAccessKeyId;
    }

    @JsonProperty("acl")
    public String getAcl() {

        return this.acl;
    }

    @JsonProperty("acl")
    public void setAcl(String acl) {
        this.acl = acl;
    }

    @JsonProperty("key")
    public String getKey() {

        return this.key;
    }

    @JsonProperty("key")
    public void setKey(String key) {
        this.key = key;
    }

    @JsonProperty("signature")
    public String getSignature() {

        return this.signature;
    }

    @JsonProperty("signature")
    public void setSignature(String signature) {
        this.signature = signature;
    }

    @JsonProperty("policy")
    public String getPolicy() {

        return this.policy;
    }

    @JsonProperty("policy")
    public void setPolicy(String policy) {
        this.policy = policy;
    }

    @JsonProperty("Content-Type")
    public String getContentType() {

        return this.contentType;
    }

    @JsonProperty("Content-Type")
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}