require "json"

require "httpclient"


class LumidatumClient
  attr_accessor :authentication_token
  attr_accessor :model_id
  attr_accessor :host_address
  attr_accessor :http_client
  attr_accessor :file_handler

  def initialize(authentication_token, model_id=nil, host_address="https://www.lumidatum.com", http_client=nil, file_handler=File)
    if authentication_token == nil
      raise ArgumentError, "authentication_token must not be nil"
    end

    @authentication_token = authentication_token
    @model_id = model_id
    @host_address = host_address.chomp("/")

    if http_client == nil
      @http_client = HTTPClient.new
    else
      @http_client = http_client;
    end

    @file_handler = file_handler
  end


  def getItemRecommendations(parameters, options = {})
    options = {
      model_id: nil,
      deserialize_response: true
    }.merge(options)

    model_id = _getModelIdOrError(options[:model_id])

    return api("POST", "api/predict/#{model_id}", nil, parameters, deserialize_response: options[:deserialize_response])
  end

  def getUserRecommendations(parameters, options = {})
    options = {
      model_id: nil,
      deserialize_response: true
    }.merge(options)

    model_id = _getModelIdOrError(options[:model_id])

    return api("POST", "api/predict/#{model_id}", nil, parameters, deserialize_response: options[:deserialize_response])
  end


  # Data upload
  def sendUserData(options = {})
    options = {
      data_string: nil,
      file_path: nil,
      model_id: nil
    }.merge(options)

    model_id = _getModelIdOrError(options[:model_id])

    return sendData("users", options[:data_string], options[:file_path], model_id)
  end

  def sendItemData(options = {})
    options = {
      data_string: nil,
      file_path: nil,
      model_id: nil
    }.merge(options)

    model_id = _getModelIdOrError(options[:model_id])

    return sendData("items", options[:data_string], options[:file_path], model_id)
  end

  def sendTransactionData(options = {})
    options = {
      data_string: nil,
      file_path: nil,
      model_id: nil
    }.merge(options)

    model_id = _getModelIdOrError(options[:model_id])

    return sendData("transactions", options[:data_string], options[:file_path], model_id)
  end

  def sendData(data_type, data_string, file_path, model_id)
    model_id = _getModelIdOrError(model_id)

    url_endpoint = "api/data"

    if data_string != nil
      url_query_parameters = {:model_id => model_id, :data_type => data_type}

      return api("POST", url_endpoint, url_query_parameters, data_string, deserialize_response: false)
    else
      file_name = File.basename(file_path)
      file_size = nil
      presigned_response_object = getPresignedResponse(
        nil,
        model_id,
        data_type: data_type,
        file_name: file_name,
        file_size: file_size
      )
    end

    @file_handler.open(file_path) do |upload_file|
      form_fields = presigned_response_object["fields"]
      form_fields["file"] = upload_file

      return @http_client.post(presigned_response_object["url"], form_fields)
    end
  end

  # File download
  def getLatestLTVReport(download_file_path, options = {})
    options = {
      model_id: nil,
      zipped: true,
      stream_download: true
    }.merge(options)

    model_id = _getModelIdOrError(options[:model_id])

    latest_key_name = getAvailableReports("LTV", model_id, zipped: options[:zipped])

    presigned_response_object = getPresignedResponse(latest_key_name, model_id, is_download: true)

    report_response = @http_client.get(presigned_response_object["url"])
    open(download_file_path, "wb") do |file|
      file.write(report_response.body)
    end

    return report_response
  end

  def getLatestSegmentationReport(download_file_path, options = {})
    options = {
      model_id: nil,
      zipped: true,
      stream_download: true
    }.merge(options)

    model_id = _getModelIdOrError(options[:model_id])

    latest_key_name = getAvailableReports("SEG", model_id, zipped: options[:zipped])
    presigned_response_object = getPresignedResponse(latest_key_name, model_id, is_download: true)

    report_response = @http_client.get(presigned_response_object["url"])
    open(download_file_path, "wb") do |file|
      file.write(report_response.body)
    end

    return report_response
  end

  def getAvailableReports(report_type, model_id, options = {})
    options = {
      zipped: true,
      latest: true
    }.merge(options)

    url_query_parameters = {:model_id => model_id, :report_type => report_type, :zipped => options[:zipped], :latest => true}
    list_reports_response = api("GET", "api/data", url_query_parameters, model_id, deserialize_response: false)
    list_reports_response_object = JSON.parse(list_reports_response.body)

    if list_reports_response.status != 200
      raise IOError, "HTTP #{list_reports_response.status}: #{list_reports_response_object["error"]} "
    end

    if list_reports_response_object["latest_key_name"] != nil

      return list_reports_response_object["latest_key_name"]
    else

      return list_reports_response_object["available_key_names"]
    end
  end

  def getPresignedResponse(key_name, model_id, options = {})
    options = {
      data_type: nil,
      file_name: nil,
      file_size: nil,
      is_download: false
    }.merge(options)

    parameters = {"model_id" => model_id}

    if options[:is_download]
      parameters["key_name"] = key_name
      parameters["is_download"] = true
    else
      parameters["data_type"] = options[:data_type]
      parameters["file_name"] = options[:file_name]
      parameters["file_size"] = options[:file_size]
      parameters["presigned_url_http_method"] = "PUT"
    end

    return api("POST", "api/data", nil, parameters)
  end


  def api(http_method, url_endpoint, url_query_parameters, parameters, options = {})
    options = {
      deserialize_response: true
    }.merge(options)

    formatted_url = "#{@host_address}/#{url_endpoint}"
    if url_query_parameters
      formatted_url = formatted_url + "?" + URI.encode_www_form(url_query_parameters)
    end

    headers = {"authorization" => @authentication_token, "content-type" => "application/json"}

    if http_method == "GET"
      api_response = @http_client.get(formatted_url, header: headers)
    elsif http_method == "POST"
      if parameters.class != String
        parameters_str = JSON.generate(parameters)
      else
        parameters_str = parameters
      end
      api_response = @http_client.post(formatted_url, header: headers, body: parameters_str)
    end

    if options[:deserialize_response]
      if api_response.status == 200 or api_response.status == 201

        return JSON.parse(api_response.body)
      end
    else

      return api_response
    end
  end


  def _getModelIdOrError(model_id)
    model_id = if model_id == nil then @model_id else model_id end
    if model_id == nil
      raise ArgumentError, "model_id must be set during client instantiation or provided during your instance method call."
    end

    return model_id
  end
end
