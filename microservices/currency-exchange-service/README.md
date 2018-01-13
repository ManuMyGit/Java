# Currency-exchange-service service
- This is a service which is configured through spring cloud config. You have only to write down the name of the application and the URL of the config server.
- The config file is called bootstrap.properties (the application.properties must be changed for that one)
- This service is published in Eureka
- It uses JPA to access to database and JpaRepository