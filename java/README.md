#Lumidatum Java Client Documentation:

####Client:

#####com.lumidatum.Client
`Client()`

`Client(String hostAddress, String authenticationToken)`

`Client(String hostAddress, long modelId, String authenticationToken)`
<br />
throws `IllegalArgumentException` for `modelId` values less than `1`

#####methods

`Client.postPredict(ProductPostParameters parameters)`
<br />
returns `ProductResponse`

`Client.postPredict(long modelId, ProductPostParameters parameters)`
<br />
returns `ProductResponse`

`Client.postPredict(AuctionPostParameters parameters)`
<br />
returns `AuctionResponse`

`Client.postPredict(long modelId, AuctionPostParameters parameters)`
<br />
returns `AuctionResponse`


####PostParameters:

#####com.lumidatum.client.ProductPostParameters
`ProductPostParameters()`

`ProductPostParameters(long customerId, long orderId, List<Long> includedProductIds, List<Long> excludedProductIds, int numberOfRecommendations)`

#####com.lumidatum.client.AuctionPostParameters
`AuctionPostParameters()`

`AuctionPostParameters(long bidderId, List<Long> includedItemIds, List<Long> excludedItemIds, int numberOfRecommendations)`


####Responses:

#####com.lumidatum.client.ProductResponse
`ProductResponse()`

#####methods

`ProductResponse.getModelId()`
<br />
returns `long`

`ProductResponse.setModelId(long modelId)`
<br />
returns `void`

`ProductResponse.getOrderId()`
<br />
return `long`

`ProductResponse.setOrderId(long orderId)`
<br />
returns `void`

`ProductResponse.getCustomerId()`
<br />
returns `long`

`ProductResponse.setCustomerId(long customerId)`
<br />
returns `void`

`ProductResponse.getCreatedAt()`
<br />
returns `java.util.Date`

`ProductResponse.setCreatedAt(java.util.Date createdAt)`
<br />
returns `void`

`ProductResponse.getRecommendations()`
<br />
returns `java.util.List<com.lumidatum.client.ProductRecommendation>`

`ProductResponse.setRecommendations(java.util.List<com.lumidatum.client.ProductRecommendation> recommendations)`
<br />
returns `void`

<br />

#####com.lumidatum.client.AuctionResponse
`AuctionResponse()`

#####methods

`AuctionResponse.getModelId()`
<br />
returns `long`

`AuctionResponse.setModelId(long modelId)`
<br />
returns `void`

`ProductResponse.getPostingId()`
<br />
returns `long`

`ProductResponse.setPostingId(long postingId)`
<br />
returns `void`

`ProductResponse.getBidderId()`
<br />
returns `long`

`ProductResponse.setBidderId(long bidderId)`
<br />
returns `void`

`ProductResponse.getCreatedAt()`
<br />
returns `java.util.Date`

`ProductResponse.setCreatedAt(java.util.Date createdAt)`
<br />
returns `void`

`AuctionResponse.getRecommendations()`
<br />
returns `java.util.List<com.lumidatum.client.AuctionRecommendation>`

`AuctionResponse.setRecommendations(java.util.List<com.lumidatum.client.AuctionRecommendation> recommendations)`
<br />
returns `void`


####Recomendations:

#####com.lumidatum.client.ProductRecommendation
`ProductRecommendation()`

`ProductRecommendation(long productId)`

`ProductRecommendation(String productDescription)`

`ProductRecommendation(long productId, String productDescription)`

#####methods

`ProductRecommendation.getProductId()`
<br />
returns `long`

`ProductRecommendation.setProductId(long productId)`
<br />
returns `void`

`ProductRecommendation.getProductDescription()`
<br />
returns `String`

`ProductRecommendation.setProductDescription(String productDescription)`
<br />
returns `void`

<br />

#####com.lumidatum.client.AuctionRecommendation
`AuctionRecommendation()`

`AuctionRecommendation(long itemId)`

`AuctionRecommendation(String itemDescription)`

`AuctionRecommendation(long itemId, String itemDescription)`

#####methods

`AuctionRecommendation.getItemId()`
<br />
returns `long`

`AuctionRecommendation.setItemId(long itemId)`
<br />
returns `void`

`AuctionRecommendation.getItemDescription()`
<br />
returns `String`

`AuctionRecommendation.setItemDescription(String itemDescription)`
<br />
returns `void`


##API:

###POST /api/predict/<modelId>


####Product recommender:

#####Parameters:

`long modelId` (will override modelId set in the Client instance for a given postPredict call, is required if no modelId was set at Client instantiation.)

`ProductPostParameters parameters`

#####Returns:

`ProductResponse response`

#####Example with Client:
```java

```



####Auction recommender:

#####Parameters:

`long modelId` (will override modelId set in the Client instance for a given postPredict call, is required if no modelId was set at Client instantiation)

`AuctionPostParameters parameters`

#####Returns:

`AuctionResponse response`

#####Example with Client:
```java
```


