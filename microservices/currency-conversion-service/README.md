# Currency-exchange-service service
- This is a service which is configured through spring cloud config. You have only to write down the name of the application and the URL of the config server.
- The config file is called bootstrap.properties (the application.properties must be changed for that one)
- This service is published in Eureka
- It uses currency-exchange-service. To do it, Feign, Ribbon and Zuul are used as REST client, load balancer and API Gateway.
	- To enable Feign you have to mark the main class as @EnableFeignClients (you have to say in which packages feign clients are).
	- To create the client you need to create an interface to mark it as @FeignClient(name = "name of the zuul server")
	- The only thing you need to do is to add as many methods as you need in this interface and inject it in the classs you need. Due to Zuul, in each method, in the URI, you need to concat the name of the service to the original URI.
	- To enable Ribbon you have to mark the feign client as @RibbonClient(name = "name of the service in server name")