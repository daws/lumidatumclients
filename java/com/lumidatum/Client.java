package com.lumidatum;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
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
import com.lumidatum.client.PreSendFileResponse;
import com.lumidatum.client.SendFileResponse;


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

    public SendFileResponse sendUserProfilesFile(String inputFilePath) throws IOException, JsonProcessingException {
        File inputFile = new File(inputFilePath);

        return this.sendUserProfilesFile(inputFile);
    }

    public SendFileResponse sendUserProfilesFile(File inputFile) throws IOException, JsonProcessingException {

        return this._sendFileRequest(inputFile, "transactions");
    }

    public SendFileResponse sendItemProfilesFile(String inputFilePath) throws IOException, JsonProcessingException {
        File inputFile = new File(inputFilePath);

        return this.sendItemProfilesFile(inputFile);
    }

    public SendFileResponse sendItemProfilesFile(File inputFile) throws IOException, JsonProcessingException {

        return this._sendFileRequest(inputFile, "transactions");
    }

    public SendFileResponse sendTransactionsFile(String inputFilePath) throws IOException, JsonProcessingException {
        File inputFile = new File(inputFilePath);

        return this.sendTransactionsFile(inputFile);
    }

    public SendFileResponse sendTransactionsFile(File inputFile) throws IOException, JsonProcessingException {

        return this._sendFileRequest(inputFile, "transactions");
    }

    private <ResponseType> ResponseType _postPredict(IPostParameters parameters, Class responseTypeClass) throws IOException, JsonProcessingException {
        return this.<ResponseType>_postPredict(0, parameters, responseTypeClass);
    }

    private <ResponseType> ResponseType _postPredict(long modelId, IPostParameters parameters, Class responseTypeClass) throws IOException, JsonProcessingException {
        if (this.modelId != 0) {
            modelId = this.modelId;
        }

        // Serialize input parameters
        String payload;

        try {
            payload = parameters.serializeToJson();
        } catch (JsonProcessingException e) {
            throw e;
        }

        String rawResponseBodyString = _sendPostRequest("%s/api/predict/%d", modelId, payload);
        ResponseType deserializedResponse = this.<ResponseType>_deserializeResponse(rawResponseBodyString, responseTypeClass);

        return deserializedResponse;
    }

    private String _sendPostRequest(String urlPattern, long modelId, String payload) {
        String responseContent = null;

        try {
            URL url = new URL(String.format(urlPattern, this.hostAddress, modelId));

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", this.authenticationToken);
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.flush();
            writer.close();

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuffer jsonData = new StringBuffer();
            String line;
            while ((line = br.readLine()) != null) {
                jsonData.append(line);
            }
            br.close(); 

            connection.disconnect();
            responseContent = jsonData.toString();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return responseContent;
    }

    private SendFileResponse _sendFileRequest(File inputFile, String dataType) throws IOException {
        // Check this gets just the name, not the String representation of the full path...
        PreSendFileResponse preSendFileResponse = this._preSendFileRequest(inputFile, dataType);
        URL uploadUrl = preSendFileResponse.getUploadUrl();

        System.out.println(preSendFileResponse.getUploadUrl());

        SendFileResponse sendFileResponse;

        if (uploadUrl.toString().startsWith("https://lumidatum-app-data")) {
            sendFileResponse = this._sendFileToS3(preSendFileResponse, inputFile);
        } else {
            sendFileResponse = this._sendFileToWebServer(preSendFileResponse, inputFile);
        }

        return sendFileResponse;
    }

    private PreSendFileResponse _preSendFileRequest(File inputFile, String dataType) throws IOException {
        String inputFileName = inputFile.getName();
        String rawPreSendFileResponseString = this._sendPostRequest("%s/api/data?model_id=%d&data_type=transactions&file_name=" + inputFileName, modelId, "");

        return this.<PreSendFileResponse>_deserializeResponse(rawPreSendFileResponseString, PreSendFileResponse.class);
    }

    private SendFileResponse _sendFileToS3(PreSendFileResponse preSendFileResponse, File inputFile) throws IOException {
        URL uploadUrl = preSendFileResponse.getUploadUrl();

        return new SendFileResponse();
    }

    // Code modified from example at:
    // https://ashwinrayaprolu.wordpress.com/2010/11/16/upload-file-to-server-from-java-client-without-any-library/
    private SendFileResponse _sendFileToWebServer(PreSendFileResponse preSendFileResponse, File inputFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(inputFile);

        URL url = preSendFileResponse.getUploadUrl();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);

        // Use a post method.
        connection.setRequestMethod("POST");

        String twoHyphens = "--";
        String boundary = "***232404jkg4220957934FW**";
        String lineEnd = "\r\n";

        connection.setRequestProperty("Connection", "Keep-Alive");
        connection.setRequestProperty("Authorization", this.authenticationToken);
        connection.setRequestProperty("Content-Type",
        "multipart/form-data;boundary=" + boundary);

        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());

        dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
        dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"upload_file\";"
        + " filename=\"" + inputFile.getName() + "\"" + lineEnd);
        dataOutputStream.writeBytes(lineEnd);

        // Create a buffer of maximum size
        int maxBufferSize = 1 * 1024 * 1024;
        int bytesAvailable = fileInputStream.available();
        int bufferSize = Math.min(bytesAvailable, maxBufferSize);
        byte[] buffer = new byte[bufferSize];

        // Read file and write it into form...
        int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

        while (bytesRead > 0) {
            dataOutputStream.write(buffer, 0, bufferSize);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);
        }

        // Send multipart form data necesssary after file data...
        dataOutputStream.writeBytes(lineEnd);
        dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

        // Close streams
        fileInputStream.close();
        dataOutputStream.flush();
        dataOutputStream.close();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuffer jsonData = new StringBuffer();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            jsonData.append(line);
        }
        bufferedReader.close(); 
        connection.disconnect();

        SendFileResponse sendFileResponse = this.<SendFileResponse>_deserializeResponse(jsonData.toString(), SendFileResponse.class);

        return sendFileResponse;
    }

    private <ResponseType> ResponseType _deserializeResponse(String rawResponseBodyString, Class responseTypeClass) throws IOException, JsonProcessingException {
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

    private void validateModelId(long modelId) {
        if (modelId < 1) {
            throw new IllegalArgumentException();
        }
    }
}