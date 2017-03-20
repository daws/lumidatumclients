require "minitest/autorun"
require "./lib/lumidatum_client.rb"


class ClientInit < Minitest::Test
  def setup
    @valid_api_token = "Token <API key>"
    @valid_model_id = 123
    @custom_host_address = "http://localhost:8000"

    @default_host_address = "https://www.lumidatum.com"
  end

  # No specified host/default host
  def test_client_init
    test_client = LumidatumClient.new(@valid_api_token, @valid_model_id, @custom_host_address)

    assert_instance_of(LumidatumClient, test_client)
  end

  def test_default_host
    test_client = LumidatumClient.new(@valid_api_token, @valid_model_id)

    # Default host should be https://www.lumidatum.com
    assert_equal(@default_host_address, test_client.host_address)
  end

  def test_specifying_a_host
    test_client = LumidatumClient.new(@valid_api_token, @valid_model_id, @custom_host_address)

    assert_equal(@custom_host_address, test_client.host_address)
  end

  def test_nil_model_id_error
    assert_raises ArgumentError do
      LumidatumClient.new(@valid_api_token, nil)
    end
  end

  def test_nil_api_token_error
    assert_raises ArgumentError do
      LumidatumClient.new(nil, @valid_model_id)
    end
  end
end


def createTestClient

  return LumidatumClient.new("API Key", 123)
end


class Personalization
  def setup
    @test_client = createTestClient
  end

  def test_item_recs
    test_recommendations = []
    # Set httpclient stub to return test_recommendations
    recommendations = @test_client.getItemRecommendations({})

    assert_equal(test_recommendations, recommendations)
  end
end

class UploadDataFiles < Minitest::Test
  def setup
    @test_client = createTestClient
  end

  def test_sending_file
    file_upload_response = @test_client.sendTransactionData("path/to/transactions_data_file.csv")

    assert_equal(201, file_upload_response.status)
  end
end

class DownloadReports < Minitest::Test
  def setup
    @test_client = createTestClient
  end

  def test_getting_report
    file_download_response = @test_client.getLatestLTVReport("path/to/download_file.csv")

    assert_equal(200, file_download_response.status)
  end
end
