# This is a collection of microservices to show how Spring Cloud Microservices work.
In this section you can see all required components to implement microservices with Sprint Cloud Microservices.
# [Spring Cloud Config Server](https://github.com/ManuMyGit/Java/tree/master/microservices/spring-cloud-config-server)
Spring Cloud Config provides server and client-side support for externalized configuration in a distributed system. With the Config Server you have a central place to manage external properties for applications across all environments. The concepts on both client and server map identically to the Spring Environment and PropertySource abstractions, so they fit very well with Spring applications, but can be used with any application running in any language ([ref.](https://cloud.spring.io/spring-cloud-config/)).
In addition to this, in this example you can see the following features:
- Use of profiles to have different configurations for all your environments (dev, qa, pro, ...).
- Use of Spring Cloud Bus to refresh all instances configuration in one step.
# [Netflix Eureka Naming Server](https://github.com/ManuMyGit/Java/tree/master/microservices/netflix-eureka-naming-server)
Eureka is a REST (Representational State Transfer) based service that is primarily used in the AWS cloud for locating services for the purpose of load balancing and failover of middle-tier servers. We call this service, the Eureka Server. Eureka also comes with a Java-based client component, the Eureka Client, which makes interactions with the service much easier. The client also has a built-in load balancer that does basic round-robin load balancing. At Netflix, a much more sophisticated load balancer wraps Eureka to provide weighted load balancing based on several factors like traffic, resource usage, error conditions etc to provide superior resiliency ([ref.](https://github.com/Netflix/eureka/wiki/Eureka-at-a-glance)).
In addition to this, in this example you can see the following features:
- Client configuration.
- Spring boot actuator (see below).
# Spring Boot Actuators
Actuators enable production-ready features to a Spring Boot application – without having to actually implement these things yourself. They’re mainly used to expose different types of information about the running application – health, metrics, info, dump, env etc. And while these are no replacement for a production-grade monitoring solution – they’re a very good starting point ([ref.](http://www.baeldung.com/spring-boot-actuators)).
Most services in this section have avaliable actuator metrics (http://localhost:port/application/index.html).
# Zuul API Gateway
Zuul is an edge service that provides dynamic routing, monitoring, resiliency, security, and more ([ref.](https://github.com/Netflix/zuul)).
In addition to this, in this example a loggin filter is implemented.
# Zipkin Distributed Tracing Server
Zipkin is a distributed tracing system. It helps gather timing data needed to troubleshoot latency problems in microservice architectures. It manages both the collection and lookup of this data. Zipkin’s design is based on the Google Dapper paper ([ref.](https://github.com/openzipkin/zipkin)).
# Services avaliables
- Limits service.
    - This service return a minimun a maximum value which are used to check if a value can be converted into other one.
    - Characteristics:
        - It uses config server to get its configuration to know the minimum and maximum value.
        - It uses Spring Bus to "know" configuration file changes.
        - It is exposed in Eureka naming server.
        - It uses Hystrix for latency and fault tolerance.
        - It uses actuator.
        - It uses sleuth, zipkin and amqp (Rabbit MQ) to centralized log.
- Currency exchange service.
    - This service returns whether a currency can be converted into other one or not.
    - Characteristics:
        - It uses config server to retrieve its configuration.
        - It is exposed in Eureka naming server.
        - It uses JPA Repository to obtain the avaliable currencies in a embebed database (H2).
        - It uses actuator and hal browser.
        - It uses sleuth, zipkin and amqp (Rabbit MQ) to centralized log.
- Currency conversion service.
    - This service returns the conversion of a currency into other one. It uses limits service and exchange service.
    - Characteristics:
        - It uses limit service to know if the quantity to convert is between a minimum and a maximun value.
        - It uses exchange service to know wheter the conversion currency is avaliable or not.
        - It uses config server to retrieve its configuration.
        - It is exposed in Eureka naming server.
        - It uses feign as a http client.
        - It uses ribbon to client load balancing.
        - It uses actuator and hal browser.
        - It uses sleuth, zipkin and amqp (Rabbit MQ) to centralized log.
