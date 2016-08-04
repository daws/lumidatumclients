import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import com.lumidatum.IClient;
import com.lumidatum.Client;
import com.lumidatum.client.IPostParameters;
import com.lumidatum.client.AuctionPostParameters;
import com.lumidatum.client.AuctionResponse;
import com.lumidatum.client.AuctionRecommendation;
import com.lumidatum.client.ProductPostParameters;
import com.lumidatum.client.ProductResponse;
import com.lumidatum.client.ProductRecommendation;


public class LumidatumExampleCall {

    public LumidatumExampleCall() {
    }

    public static void main(String[] args) throws IOException, JsonProcessingException {
        String hostAddress = "http://www.lumidatum.com"
        long modelId = <Your model Id>;
        String authenticationToken = "<Your authentication token>";

        IClient client = new Client(hostAddress, modelId, authenticationToken);

        long customerId = 123;
        long orderId = 0;
        List<Long> includeProductIds = new ArrayList<Long>();
        includeProductIds.add(Long.valueOf(1));
        includeProductIds.add(Long.valueOf(2));
        includeProductIds.add(Long.valueOf(3));
        List<Long> excludeProductIds = new ArrayList<Long>();
        excludeProductIds.add(Long.valueOf(4));
        excludeProductIds.add(Long.valueOf(5));

        int numberOfRecommendations = 5;

        // API input parameters for product recommendation prediction.
        ProductPostParameters parameters = new ProductPostParameters(
            customerId,
            orderId,
            includeProductIds,
            excludeProductIds,
            numberOfRecommendations
        );

        System.out.println(String.format("(Shop params) Test Example Request to: %s", hostAddress));
        ProductResponse lumidatumProductResponse;

        try {
            lumidatumProductResponse = client.postPredict(parameters);
        } catch (JsonProcessingException e) {
            throw e;
        }

        System.out.println("(Product params) Response:");
        System.out.println(lumidatumProductResponse.getOrderId());
        System.out.println(lumidatumProductResponse.getCustomerId());
        System.out.println(lumidatumProductResponse.getCreatedAt());

        for (ProductRecommendation recommendation : lumidatumProductResponse.getRecommendations()) {
            System.out.println(
                String.format(
                    "Product description: %s, Product Id: %d",
                    recommendation.getProductDescription(), 
                    recommendation.getProductId()
                )
            );
        }

        // long bidderId = 123;
        // List<Long> includeItemIds = new ArrayList<Long>();
        // includeItemIds.add(Long.valueOf(1));
        // includeItemIds.add(Long.valueOf(2));
        // includeItemIds.add(Long.valueOf(3));
        // List<Long> excludeItemIds = new ArrayList<Long>();
        // excludeItemIds.add(Long.valueOf(4));
        // excludeItemIds.add(Long.valueOf(5));

        // int numberOfRecommendations = 5;

        // AuctionPostParameters auctionParameters = new AuctionPostParameters(bidderId, includeItemIds, excludeItemIds, numberOfRecommendations);

        // System.out.println(String.format("(Auction params) Test Example Request to: %s", hostAddress));
        // AuctionResponse lumidatumAuctionResponse;

        // try {
        //     lumidatumAuctionResponse = client.postPredict(auctionParameters);
        // } catch (JsonProcessingException e) {
        //     throw e;
        // }

        // System.out.println("(Auction params) Response:");
        // System.out.println(lumidatumAuctionResponse.getPostingId());
        // System.out.println(lumidatumAuctionResponse.getBidderId());
        // System.out.println(lumidatumAuctionResponse.getCreatedAt());

        // for (AuctionRecommendation recommendation : lumidatumAuctionResponse.getRecommendations()) {
        //     System.out.println(String.format("Item description: %s, Item Id: %d", recommendation.getItemDescription(), recommendation.getItemId()));
        // }
    }
}