# coding: utf-8
lib = File.expand_path('../lib', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.include?(lib)

Gem::Specification.new do |spec|
  spec.name          = "lumidatum_client"
  spec.version       = "0.1.3"
  spec.authors       = ["Mat Lee"]
  spec.email          = ["matt@lumidatum.com"]
  spec.summary       = %q{}
  spec.description   = %q{}
  spec.homepage      = "https://www.lumidatum.com"
  spec.license       = "MIT"

  spec.files         = ["lib/lumidatum_client.rb"]
  spec.executables   = ["lumidatum_client"]
  spec.test_files    = ["tests/test_client_unit_tests.rb"]
  spec.require_paths = ["lib"]

  spec.add_runtime_dependency("httpclient", "~> 2.8")
end
