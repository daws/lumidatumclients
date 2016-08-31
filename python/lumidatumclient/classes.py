import requests


class LumidatumClient(object):

    def __init__(self, authentication_token, model_id=None, host_address='https://www.lumidatum.com'):
        self.authentication_token = authentication_token
        self.model_id = model_id
        self.host_address = host_address

    def getRecommendations(self, parameters, model_id=None):
        selected_model_id = model_if if model_id else self.model_id

        response = requests.post(
            os.path.join(self.host_address, 'api/predict', selected_model_id),
            parameters
        )

        return response.json()
