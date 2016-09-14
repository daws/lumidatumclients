#Lumidatum Python Client Documentation:

###Quick Start:

To get the client in Python simply run:

`pip install lumidatumclient`

[Examples](#examples) below.

<i>Latest version: 0.3.0</i>

<i>*Currently Python versions 2.6, 2.7, 3.5 are supported.</i>

####Client:

`lumidatumclient.LumidatumClient(auth_token, model_id=None, host_address='https://www.lumidatum.com')`

`model_id` may be a string or int, and if not provided at client instantiation, must be provided in all method calls.

#####Methods:

`lumidatumclient.LumidatumClient.getRecommendations(parameters, model_id=None)`

Input parameters is a single dictionary. The key names and value types vary by model implementation.
If `model_id` is provided both at client instantiation and in a method call, the value provided at the method call is used to call the API.


#####Example with Client:<a name="examples"></a>

```
from lumidatumclient import LumidatumClient

auth_token = '<your auth token>'
model_id = <your model id>

client = LumidatumClient(auth_token, model_id)

params = {<your model params>}

recs = client.getRecommendations(params)
```
