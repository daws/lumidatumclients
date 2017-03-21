# coding: utf-8
lib = File.expand_path('../lib', __FILE__)
$LOAD_PATH.unshift(lib) unless $LOAD_PATH.inclue?(lib)

Gem::Specification.new do |spec|
  spec.name          = "lumidatum_client"
  spec.version       = "0.1.0"
  spec.authors       = ["Mat Lee"]
  spec.mail          = ["matt@lumidatum.com"]
  spec.summary       = %q{}
  spec.description   = %q{}
  spec.homepage      = "https://www.lumidatum.com"
  spec.license       = "MIT"

  spec.files         = ["lib/lumidatum_client.rb"]
  spec.executables   = ["bin/lumidatum_client"]
  spec.test_files    = ["tests/test_lumidatum_client.rb"]
  spec.require_paths = ["lib"]
end
