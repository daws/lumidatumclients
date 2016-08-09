#Lumidatum Java Client Documentation:

####Client:

`Client()`

`Client(String hostAddress, String authenticationToken)`

`Client(String hostAddress, long modelId, String authenticationToken)`
<br>
throws `IllegalArgumentException` for `modelId` values less than `1`


`Client.postPredict(ProductPostParameters parameters)`
<br>
returns `ProductResponse`

`Client.postPredict(long modelId, ProductPostParameters parameters)`
<br>
returns `ProductResponse`

`Client.postPredict(AuctionPostParameters parameters)`
returns `AuctionResponse`

`Client.postPredict(long modelId, AuctionPostParameters parameters)`
returns `AuctionResponse`


####PostParameters:

`ProductPostParameters()`
`ProductPostParameters(long customerId, long orderId, List<Long> includedProductIds, List<Long> excludedProductIds, int numberOfRecommendations)`

`AuctionPostParameters()`
`AuctionPostParameters(long bidderId, List<Long> includedItemIds, List<Long> excludedItemIds, int numberOfRecommendations)`


####Responses:

`ProductResponse()`

`AuctionResponse()`


####Recomendations:

`ProductRecommendation()`

`AuctionRecommendation()`


##API:

###POST /api/predict/<modelId>


####Product recommender:

#####Parameters:

long modelId (will override modelId set in the Client instance for a given postPredict call, is required if no modelId was set at Client instantiation.)
ProductPostParameters parameters

#####Returns:

ProductResponse response

#####Example:



####Auction recommender:

`long modelId` (will override modelId set in the Client instance for a given postPredict call, is required if no modelId was set at Client instantiation)

`AuctionPostParameters parameters`

#####Returns:

AuctionResponse response

#####Example:


