# Lumidatum Ruby Client Documentation:

### Quick Start:

To get the client in Ruby simply run:

`gem install lumidatum_client`

<i>Latest version: 0.1.4</i>

<i>*Currently Ruby version 2.4.0 is supported</i>

#### Client:

`LumidatumClient.new(auth_token, model_id=nil, host_address='https://www.lumidatum.com')`

`model_id` may be a String or Integer, and if not provided at client instantiation, must be provided in all method calls.

##### Methods:

`LumidatumClient.getRecommendations(parameters, model_id: nil)`

Input `parameters` is a single Hash. The key names and value types vary by model implementation. If `model_id` is provided both at client instantiation and in a method call, the value provided at the method call is used to call the API.

`LumidatumClient.sendUserData(data_string: nil, file_path: nil, model_id: nil)`

Writes/updates user profile data.

Requires either `data_string` or `file_path` to be provided, and `model_id` if `model_id` was not specified at client instantiation.

The data string parameter (`data_string`) should in the format below.

<i>(format type 1)</i>
```
{JSON notation}
{JSON notation}
...
{JSON notation}
```

where each line is a string representation of an object in JSON notation, or a single string representation of an array of objects in JSON notation, such as below.

<i>(format type 2)</i>
```
[
  {JSON notation},
  {JSON notation},
  ...
  {JSON notation}
]
```
Alternatively, the local path to a file to be uploaded can be provided, where the contents of the file should be (format type 1). In the case where both data_string and file_path parameters are provided, the method will prefer data_string. If model_id is specified in the method call, it will be preferred over the model_id provided at client instantiation.

`LumidatumClient.sendItemData(data_string: nil, file_path: nil, model_id: nil)`

Writes/updates item profile data.

`LumidatumClient.sendTransactionData(data_string: nil, file_path: nil, model_id: nil)`

Writes/updates transaction data.

`LumidatumClient.getLatestLTVReport(download_file_path, zipped: True)`

Gets the latest user lifetime value report.

`download_file_path` should be a string and will also be used to set the file name of the download.

`zipped` should be a boolean value indicating whether or not to download a zipped version of the requested report.

`lumidatumclient.LumidatumClient.getLatestSegmentationReport(download_file_path, zipped=True, stream_download=True)`

Gets the latest user segmentation report.

##### Examples using the Client:

###### Getting recommendations

```
require 'lumidatum_client'

auth_token = '<your auth token>'
model_id = <your model id>

client = LumidatumClient(auth_token, model_id)

params = {<your model params>}

recs = client.getRecommendations(params)
```

###### Updating user data

```
user_data_string = '{"id": 123 ... "field": "values, text, whatever\'s fine"}\n{"id": 345, ... "field": "values \\n with new lines"}'

user_data_update_response = client.sendUserData(user_data_string)
```

###### Updating transaction data

```
# Sending data in a request body
transaction_data_string = '...'

transaction_data_update_response = client.sendTransactionData(transaction_data_string)

# Uploading a file
my_local_file_path = '<my local file path>'

file_upload_response = client.sendTransactionData(file_path: my_local_file_path)
```
