# This is an example to return either JSON or XML in the request according to the header request
- HTTP verbs used: GET
- URI: /webservices/content
- To return JSON: (Headers: Accept = application/json) 
- To return XML: (Headers: Accept = application/xml)

# Actuator
- URL: http://localhost:8080/application.html
- No user needed

# Test
- Basic example of testing with MockMvc and reactive WebTestClient