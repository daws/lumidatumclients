#Lumidatum Java Client Documentation:

###Quick Start:

Simply get the compiled jar (lumidatum-client.1.1.0.jar) and add it to your class path.
You can find the jar in the repository at <a href="https://github.com/Lumidatum/lumidatumclients/tree/master/java/resources">`lumidatumclients/java/resources`</a>.
[Examples](#examples) below.

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

`Client.sendUserProfilesFile(String inputFilePath)`
<br />
returns `SendFileResponse`

`Client.sendUserProfilesFile(File inputFile)`
<br />
returns `SendFileResponse`

`Client.sendItemProfilesFile(String inputFilePath)`
<br />
returns `SendFileResponse`

`Client.sendItemProfilesFile(File inputFile)`
<br />
returns `SendFileResponse`

`Client.sendTransactionsFile(String inputFilePath)`
<br />
returns `SendFileResponse`

`Client.sendTransactionsFile(File inputFile)`
<br />
returns `SendFileResponse`


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

#####com.lumidatum.client.SendFileResponse
`SendFileResponse()`


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

#####Example with Client:<a name="examples"></a>
```java
// Create an instance of a Client
String hostAddress = "http://www.lumidatum.com;
long modelId = 123;
String authenticationToken = "Token 6daf37b9612850d0d58b293825dcbd71f68b0062";

Client lumidatumClient = new Client(hostAddress, modelId, authenticationToken);

// Input relevant parameters.
long customerId = 22;
long orderId = 541;

List<Long> includeProductIds = new ArrayList<Long>();
includeProductIds.add(Long.valueOf(1));
includeProductIds.add(Long.valueOf(2));
includeProductIds.add(Long.valueOf(3));

List<Long> excludeProductIds = new ArrayList<Long>();
excludeProductIds.add(Long.valueOf(4));
excludeProductIds.add(Long.valueOf(5));

int numberOfRecommendations = 3;

ProductPostParameters parameters = new ProductPostParamters(
    customerId,
    orderId,
    includeProductIds,
    excludeProductIds,
    numberOfRecommendations
);

// Call the REST API using the client.
ProductResponse response = client.postPredict(parameters);

// Use the results.
Date recommendationCreationTime = response.getCreatedAt();
List<ProductRecommendation> recommendations = response.getRecommendations();
```



####Auction recommender:

#####Parameters:

`long modelId` (will override modelId set in the Client instance for a given postPredict call, is required if no modelId was set at Client instantiation)

`AuctionPostParameters parameters`

#####Returns:

`AuctionResponse response`

#####Example with Client:
```java
// Create an instance of a Client
String hostAddress = "http://www.lumidatum.com;
long modelId = 123;
String authenticationToken = "Token 6daf37b9612850d0d58b293825dcbd71f68b0062";

Client lumidatumClient = new Client(hostAddress, modelId, authenticationToken);

// Input relevant parameters.
long bidderId = 12;
long postingId = 332;

List<Long> includeItemIds = new ArrayList<Long>();
includeItemIds.add(Long.valueOf(1));
includeItemIds.add(Long.valueOf(2));
includeItemIds.add(Long.valueOf(3));

List<Long> excludeItemIds = new ArrayList<Long>();
excludeItemIds.add(Long.valueOf(4));
excludeItemIds.add(Long.valueOf(5));

int numberOfRecommendations = 3;

AuctionPostParamters parameters = new AuctionPostParamters(
    bidderId,
    postingId,
    includeItemIds,
    excludeItemIds,
    numberOfRecommendations
);

// Call the REST API using the client.
AuctionResponse response = client.postPredict(parameters);

// Use the results.
Date recommendationCreationTime = response.getCreatedAt();
List<AuctionRecommendation> recommendations = response.getRecommendations();
```

####Sending Data

#####Parameters:
`String inputFilePath` A string representing the path to a local file you'd like to upload.

or

`File inputFile` An instance of a File object with data you would like to upload.

##### Returns:

`SendFileResponse response`

#####Example with Client:
```
// Send additional data.
String pathToMyUserDataFile = "/my/user/data/file.txt";
client.sendUserProfilesFile(pathToMyUserDataFile);

// You can also directly pass in a File object.
File myUserDataFile = new File("/my/user/data/file.txt");
client.sendUserProfilesFile(myUserDataFile);
```
