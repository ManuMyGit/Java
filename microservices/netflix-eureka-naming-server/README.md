# Spring cloud naming server
- The objective of this server is to register all services we're going to need so that when a service needs other service, it'll answer to eureka with the name of the server and eureka will tell the location of the server.
- You need to mark the boot class as @EnableEurekaServer.
- En each client, you need to mark every one as @EnableDiscoveryClient and add to application.properties the address of eureka:
	- eureka.client.serviceUrl.defaultZone = http://localhost:1111/eureka
- You can integrate eureka and ribbon to load balance. You have only to tell Ribbon the name of the server you want to balance.