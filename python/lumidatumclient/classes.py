import os

import requests


class LumidatumClient(object):

    def __init__(self, authentication_token, model_id=None, host_address='https://www.lumidatum.com'):
        self.authentication_token = authentication_token
        self.model_id = model_id
        self.host_address = host_address

    def getRecommendations(self, parameters, model_id=None):
        """
        Get recommendations for a model specified by model_id.

        Returns a list of id/score pairs in descending order from the highest score.
        """
        selected_model_id = model_if if model_id else self.model_id
        if selected_model_id is None:
            raise ValueError('model_id must be specified either at initialization of LumidatumClient or in client method call.')

        headers = {
            'Authorization': self.authentication_token,
            'content-type': 'application/json',
        }

        response = requests.post(
            os.path.join(self.host_address, 'api/predict', selected_model_id),
            parameters,
            headers=headers
        )

        return response.json()

    def getRecommendationDescriptions(self, parameters, model_id=None):
        """
        Get human readable recommendations.
        """
        parameters['human_readable'] = True

        return self.getRecommendations(self, parameters, model_id)
