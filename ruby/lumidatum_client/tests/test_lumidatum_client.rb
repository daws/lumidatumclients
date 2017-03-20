require "minitest/autorun"
require "./lib/lumidatum_client.rb"

# assert_equal <expected>, <actual>

class ClientInit < Minitest::Test
  def setup
    @valid_api_token = "Token <API key>"
    @valid_model_id = 123
    @custom_host_address = "http://localhost:8000"

    @default_host_address = "https://www.lumidatum.com"
  end

  # No specified host/default host
  def test_client_init
    new_client = LumidatumClient.new(@valid_api_token, @valid_model_id, @custom_host_address)

    assert_instance_of(LumidatumClient, new_client)
  end

  def test_default_host
    new_client = LumidatumClient.new(@valid_api_token, @valid_model_id)

    # Default host should be https://www.lumidatum.com
    assert_equal(@default_host_address, new_client.instance_variable_get(:@host_address))
  end

  # Specified host
  def test_specifying_host
    new_client = LumidatumClient.new(@valid_api_token, @valid_model_id, @custom_host_address)

    assert_equal(@custom_host_address, new_client.instance_variable_get(:@host_address))
  end

  # No model ID error
  def test_nil_model_id_should_error
    assert_raises ArgumentError do
      LumidatumClient.new(@valid_api_token, nil)
    end
  end

  # No API token error
  def test_nil_api_token_should_error
    assert_raises ArgumentError do
      LumidatumClient.new(nil, @valid_model_id)
    end
  end
end

# Get item recs

# Upload a file

# Download a file
