package com.lumidatum;

import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.lang.StringBuffer;
import java.net.URL;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

// import org.apache.http.HttpResponse;
// import org.apache.http.client.methods.HttpPost;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.lumidatum.client.IPostParameters;
import com.lumidatum.client.AuctionPostParameters;
import com.lumidatum.client.AuctionResponse;
import com.lumidatum.client.ProductPostParameters;
import com.lumidatum.client.ProductResponse;


public class Client implements IClient {

    private long modelId;
    // TODO: make use of modelType.
    // private ModelType modelType;
    private String authenticationToken;
    private String hostAddress;
    private String lumidatumApiEndpoint;

    public Client() {
    }

    public Client(String hostAddress, String authenticationToken) {
        // TODO: make use of modelType.
        // this.modelType = modelType;
        this.authenticationToken = authenticationToken;

        this.hostAddress = hostAddress;
    }

    public Client(String hostAddress, long modelId, String authenticationToken) {
        this.validateModelId(modelId);
        this.modelId = modelId;
        // TODO: make use of modelType.
        // this.modelType = modelType;
        this.authenticationToken = authenticationToken;

        this.hostAddress = hostAddress;
        this.lumidatumApiEndpoint = String.format("%s/api/predict/%d", hostAddress, modelId);
    }

    public ProductResponse postPredict(ProductPostParameters parameters) throws IOException, JsonProcessingException {

        return this.<ProductResponse>_postPredict(parameters, ProductResponse.class);
    }

    public ProductResponse postPredict(long modelId, ProductPostParameters parameters) throws IOException, JsonProcessingException {
        this.validateModelId(modelId);

        return this.<ProductResponse>_postPredict(modelId, parameters, ProductResponse.class);
    }

    public AuctionResponse postPredict(AuctionPostParameters parameters) throws IOException, JsonProcessingException {

        return this.<AuctionResponse>_postPredict(parameters, AuctionResponse.class);
    }

    public AuctionResponse postPredict(long modelId, AuctionPostParameters parameters) throws IOException, JsonProcessingException {
        this.validateModelId(modelId);

        return this.<AuctionResponse>_postPredict(modelId, parameters, AuctionResponse.class);
    }

    private <ResponseType> ResponseType _postPredict(IPostParameters parameters, Class responseTypeClass) throws IOException, JsonProcessingException {
        return this.<ResponseType>_postPredict(0, parameters, responseTypeClass);
    }

    private <ResponseType> ResponseType _postPredict(long modelId, IPostParameters parameters, Class responseTypeClass) throws IOException, JsonProcessingException {
        // Serialize input parameters
        String payload;

        try {
            payload = parameters.serializeToJson();
        } catch (JsonProcessingException e) {
            throw e;
        }

        String rawResponseBodyString = _sendPostRequest(modelId, payload);
        System.out.println(rawResponseBodyString);

        // Deserialize results
        ObjectMapper mapper = new ObjectMapper();
        ResponseType deserializedResponse;

        try {
            deserializedResponse = (ResponseType)mapper.readValue(rawResponseBodyString, responseTypeClass);
        } catch (JsonProcessingException e) {
            System.out.println("Deserialization failure");
            System.out.println(e);
            throw e;
        } catch (IOException e) {
            System.out.println("IOException caught...");
            System.out.println(e);
            throw e;
        }

        return deserializedResponse;
    }

    private String _sendPostRequest(long modelId, String payload) {
        String responseContent = null;

        try {
            URL url;
            if (modelId == 0) {
                url = new URL(this.lumidatumApiEndpoint);
            } else {
                url = new URL(String.format("%s/api/predict/%d", this.hostAddress, modelId));
            }
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", this.authenticationToken);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);  
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer jsonString = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();

            connection.disconnect();
            responseContent = jsonString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return responseContent;
    }

    private void validateModelId(long modelId) {
        if (modelId < 1) {
            throw new IllegalArgumentException();
        }
    }
}