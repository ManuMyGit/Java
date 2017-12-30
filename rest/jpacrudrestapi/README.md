# This is an example of JPA CRUD RESTful API with exception handling
- HTTP verbs used: GET, POST, PUT, DELETE
	- URI GET: /webservices/teachers
		- Optional parameter: order (true/false) /webservices/teachers?order=true
		- Optional parameter: field (String) /webservices/teachers?order=true&field=name (default order by Id)
	- URI GET: /webservices/teachers/{id}
	- URI GET: /webservices/teachers/{id}/students
	- URI GET: /webservices/teachers/{id}/students/{id}
	- URI POST: /webservices/teachers
	- URI POST: /webservices/teachers/{id}/students
	- URI PUT: /webservices/teachers/{id}
	- URI PUT: /webservices/teachers/{id}/students/{id}
	- URI DELETE: /webservices/teachers/{id}
	- URI DELETE: /webservices/teachers/{id}/students/{id}

# Main characteristics
 - Embebed database H2 (URL to access: http://localhost:8080/h2-console; JDBC-URL: jdbc:h2:mem:testdb)
 - Update ddl on starting (schema.sql)
 - Insert data on starting (data.sql)
 - Two Jpa repositories (TeacherRepository and StudentRepository)
 - Use of personalized queries with @Query and @Param
 - Example of one-to-many relationship (1 teacher has 1 or more students).
 - Two empty custom repositories (TeacherRepositoryCustom and StudentRepositoryCustom). It allow you to create a custom method by using EntityManager.
 - Two Services (interface and implementation)
 - Two controllers
 - Use of JsonIgnoreProperties to filter properties in order to prevent loops (Teacher -> Student -> Teacher ...)
 - Use of Jpa Auditing
 - Use of Javax validations
 - Use of Optional to prevent the application from NullPointerException
 - Content negotiation (both XML and JSON)

# Exceptions
- A new format is created when an error occurs. Time, message and details are returned.
- Data not found -> returns HttpStatus.NOT_FOUND
- Invalid Data -> returns HttpStatus.BAD_REQUEST
- As you can see, in the original project there isn't exception handling and, for example, when you try to update a person who doesn't exists you get a HttpStatus.OK. However, now you get a HttpStatus.NOT_FOUND

# Actuator
- URL: http://localhost:8080/application.html
- No user needed

# Test
- Testing with TestRestTemplate