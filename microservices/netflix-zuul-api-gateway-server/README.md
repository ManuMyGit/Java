# Zull API Gateway service
-Until now, he've created some services which have interactions between them. The call is done by calling directly to the service, but this is not a good practise because if you need to do the same thing to every service, you need to reply the code.
-In order to make easier this common configuration, API gateways are used (such as Zuul). Common things that are centralized are:
	- Authentication, authorization and security.
	- Rate limits.
	- Fault tolerance.
	- Service aggregation.
	- Logging.
- To configure a API Gatewaty you need to do three steps:
	- To create a component for Zuul API Gateway
	- To decide what it should do when it intercepts a request (for example, a request from currency-conversion-service to currency-exchange-service which is intercepted by Zull). You need to implement this behavoiur.
	- To make sure tha all important requests are configured to pass through API Gateway.

# How to do it
- To create the Zuul Proxy
	- Just to create a spring boot project annotated with @EnableZuulProxy and @EnableDiscoveryClient to be published in Eureka.
	- To configure bootstrap.properties with the name of the server and the URL to spring cloud config.
	- To configure .properties to tell the port, Eureka URL, ...
- To decide what to do
	- An example of request without Zuul would be: http://localhost:8000/microservices/currency-exchange/from/EUR/to/USD
	- An example of request with Zuul (port 8765) would be: http://localhost:8765/currency-exchange-service/microservices/currency-exchange/from/EUR/to/USD (pattern http://localhost:8765/{application-name}/{uri})
	- Another possibility is to configure the file to route requests. This is an example:
	zuul:
  		routes:
    		greetings:
      			path: /greeting/**
      			serviceId: GREETING-SERVICE
      			stripPrefix: false

# How to create a filter
- You must create a class which extends ZuulFilter
- You must implement the four methods of the abstract class:
	- public boolean shouldFilter(): should this filter be executed? You can decide for example based on request datas.
	- public int filterOrder(): if you have more than one filter, such as loggin, security, ..., you can say the priority of the filters.
	- public String filterType(): when should the filter be executed? pre, routing, post or only error requests.
	- public Object run(): what the filter will do.
