#Lumidatum Java Client Documentation:

####Client:

`Client()`

`Client(String hostAddress, String authenticationToken)`

`Client(String hostAddress, long modelId, String authenticationToken)`
<br />
throws `IllegalArgumentException` for `modelId` values less than `1`


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

`ProductPostParameters()`

`ProductPostParameters(long customerId, long orderId, List<Long> includedProductIds, List<Long> excludedProductIds, int numberOfRecommendations)`

`AuctionPostParameters()`

`AuctionPostParameters(long bidderId, List<Long> includedItemIds, List<Long> excludedItemIds, int numberOfRecommendations)`


####Responses:

`ProductResponse()`

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

`AuctionResponse()`

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

`ProductRecommendation()`

`AuctionRecommendation()`


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


