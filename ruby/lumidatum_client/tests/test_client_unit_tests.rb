require "json"
require "minitest/autorun"

require "webmock/test_unit"

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
    test_recommendations = [[], [], []]
    WebMock.stub_request(
      :post,
      "https://www.lumidatum.com/api/predict/123"
    ).to_return(
      status: 200,
      body: JSON.generate(test_recommendations)
    )

    recommendations = @test_client.getItemRecommendations({})

    assert_equal(test_recommendations, recommendations)
  end
end

class UploadDataFiles < Minitest::Test
  def setup
    @test_client = createTestClient
  end

  def test_sending_file
    # Upload presign request
    WebMock.stub_request(
      :post,
      "https://www.lumidatum.com/api/data"
    ).to_return(
      status: 201,
      body: JSON.generate({"url": "http://test.upload.url"})
    )
    # S3 upload response
    WebMock.stub_request(
      :post,
      "http://test.upload.url"
    ).with(
      headers: {},
      body: JSON.generate({})
    ).to_return(
      status: 204
    )

    file_upload_response = @test_client.sendTransactionData(file_path: "path/to/transactions_data_file.csv")

    assert_equal(204, file_upload_response.status)
  end
end

class DownloadReports < Minitest::Test
  def setup
    @test_client = createTestClient
  end

  def test_getting_report
    # List response
    WebMock.stub_request(
      :get,
      "https://www.lumidatum.com/api/data?latest=true&model_id=123&report_type=LTV&zipped=true&latest=true"
    ).to_return(
      status: 200,
      body: JSON.generate({"key_name" => "test_key_name"})
    )
    # Presign response
    WebMock.stub_request(
      :post, "https://www.lumidatum.com/api/data"
    ).to_return(
      status: 200,
      body: JSON.generate({"url" => "http://test.download.url"})
    )
    # S3 download response
    WebMock.stub_request(:get, "http://test.download.url")

    file_download_response = @test_client.getLatestLTVReport("test_download_file.csv")

    assert_equal(200, file_download_response.status)

    # Clean up
    File.delete("test_download_file.csv")
  end
end
