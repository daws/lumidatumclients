import json
import os

import requests


class LumidatumClient(object):

    def __init__(self, authentication_token, model_id=None, host_address='https://www.lumidatum.com'):
        self.authentication_token = authentication_token
        self.model_id = str(model_id)
        self.host_address = host_address

    def api(self, http_method='POST', parameters={}, model_id=None, api_function='predict'):
        """
        General method for the Lumidatum REST API.
        """

        selected_model_id = str(model_id) if model_id else self.model_id
        if selected_model_id is None:
            raise ValueError('model_id must be specified either at initialization of LumidatumClient or in client method call.')

        headers = {
            'Authorization': self.authentication_token,
            'content-type': 'application/json',
        }

        api_endpoint = os.path.join(self.host_address, 'api/{}'.format(api_function), selected_model_id)

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

        return response.json()

    def getRecommendations(self, parameters, model_id=None):
        """
        Get recommendations for a model specified by model_id.

        Returns a list of id/score pairs in descending order from the highest score.
        """

        return self.api(http_method='POST', parameters=parameters, model_id=model_id)

    def getRecommendationDescriptions(self, parameters, model_id=None):
        """
        Get human readable recommendations.
        """
        parameters['human_readable'] = True

        return self.api(http_method='POST', parameters=parameters, model_id=model_id)
