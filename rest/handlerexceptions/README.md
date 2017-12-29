# This is an example of CRUD RESTful API with exception handling
- It is based on CrudRestfulExample (https://github.com/ManuMyGit/Java/tree/master/rest/crudrestful)
- HTTP verbs used: GET, POST, PUT, DELETE
	- URI GET: /webservices/person
	- URI GET: /webservices/person/{id}
	- URI POST: /webservices/person
	- URI PUT: /webservices/person/{id}
	- URI DELETE: /webservices/person/{id}

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