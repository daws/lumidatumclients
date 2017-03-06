import collections
import json
import os

import requests
import requests_toolbelt

import helpers


class LumidatumClient(object):

    def __init__(self, authentication_token, model_id=None, host_address='https://www.lumidatum.com'):
        self.authentication_token = authentication_token
        self.model_id = str(model_id)
        self.host_address = host_address.rstrip('/')


    def getSegmentation(self, parameters='', model_id=None, deserialize_response=True):

        return self.api(http_method='POST', parameters=parameters, model_id=model_id, api_function='segmentation', deserialize_response=deserialize_response)

    def getLifetimeValue(self, parameters, model_id=None, deserialize_response=True):

        return self.api(http_method='POST', parameters=parameters, model_id=model_id, api_function='customervalue', deserialize_response=deserialize_response)

    def getRecommendations(self, parameters, model_id=None, deserialize_response=True):
        """
        Get recommendations for a model specified by model_id.

        Returns a list of id/score pairs in descending order from the highest score.
        """

        return self.api(http_method='POST', parameters=parameters, model_id=model_id, api_function='predict', deserialize_response=deserialize_response)

    def getRecommendationDescriptions(self, parameters, model_id=None, deserialize_response=True):
        """
        Get human readable recommendations.
        """
        parameters = dict(parameters)
        parameters['human_readable'] = True

        return self.api(http_method='POST', parameters=parameters, model_id=model_id, api_function='predict', deserialize_response=deserialize_response)

    def api(self, http_method='POST', parameters={}, model_id=None, api_function='predict', deserialize_response=True):
        """
        General method for the Lumidatum REST API.

        If deserialize_response is set to False, a requests.HttpResponse object will be returned.
        """

        selected_model_id = str(model_id) if model_id else self.model_id
        if selected_model_id is None:
            raise ValueError('model_id must be specified either at initialization of LumidatumClient or in client method call.')

        headers = {
            'Authorization': self.authentication_token,
            'content-type': 'application/json',
        }

        api_endpoint = '{}/api/{}/{}'.format(self.host_address, api_function, selected_model_id)

        if http_method == 'POST':
            response = requests.post(
                api_endpoint,
                json.dumps(parameters),
                headers=headers
            )
        elif http_method == 'GET':
            response = requests.get(
                api_endpoint,
                headers=headers
            )
        else:
            raise ValueError('HTTP method "{}" not allowed'.format(http_method))

        if deserialize_response:
            try:

                return response.json()
            except:

                return {'error': response.text}
        else:

            return response


    # Data string takes priority, in the case of data_string and file_path params both being provided
    def sendUserData(self, data_string=None, file_path=None, model_id=None, file_upload_status_to_std_out=True):

        return self.dataUpdateApi(model_id, 'users', data_string, file_path, file_upload_status_to_std_out)

    def sendItemData(self, data_string=None, file_path=None, model_id=None, file_upload_status_to_std_out=True):

        return self.dataUpdateApi(model_id, 'items', data_string, file_path, file_upload_status_to_std_out)

    def sendTransactionData(self, data_string=None, file_path=None, model_id=None, file_upload_status_to_std_out=True):

        return self.dataUpdateApi(model_id, 'transactions', data_string, file_path, file_upload_status_to_std_out)

    def dataUpdateApi(self, model_id, data_type, data_string, file_path, file_upload_status_to_std_out):
        selected_model_id = str(model_id) if model_id else self.model_id

        if selected_model_id is None:
            raise ValueError('model_id must be specified either at initialization of LumidatumClient or in client method call.')

        if data_string:
            response = requests.post(
                '{}/api/data?model_id={}&data_type={}'.format(self.host_address, selected_model_id, data_type),
                data_string,
                headers={
                    'content-type': 'application/json',
                    'authorization': self.authentication_token,
                }
            )

            return response
        elif file_path:
            file_size = os.stat(file_path).st_size
            file_name = os.path.basename(file_path)

            presign_response = requests.post(
                '{}/api/data?model_id={}&data_type={}&file_name={}&file_size={}'.format(self.host_address, selected_model_id, data_type, file_name, file_size),
                headers={
                    'content-type': 'application/json', # Do I need this?
                    'authorization': self.authentication_token,
                }
            )
            presign_response_object = helpers.parsePresignResponse(presign_response)

            upload_response = self.sendFile(presign_response_object, file_path, file_upload_status_to_std_out)

            return upload_response
        else:
            raise ValueError('Missing argument: data_string or file_path required')

    def sendFile(self, presign_response_object, file_path, file_upload_status_to_std_out):
        with open(file_path, 'rb') as upload_file:
            destination_url = presign_response_object['url']
            fields = collections.OrderedDict(presign_response_object['fields'])
            fields['file'] = upload_file

            multipart_encoded_data  = requests_toolbelt.multipart.encoder.MultipartEncoder(fields=fields)

            response = requests.post(destination_url, data=multipart_encoded_data, headers={'Content-Type': multipart_encoded_data.content_type})

        return response
