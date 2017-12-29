#This is an example on how to version a RESTful API
1- Versioning in URL
	Example v1: /webservices/v1/person
	Example v2: /webservices/v2/person
2- Versioning with params in URL
	Example v1: /webservices/person/param?version=1
	Example v2: /webservices/person/param?version=2
3- Versioning in header
	Example v1: /webservices/person/header
		Headers: X-API-VERSION = 1
	Example v2: /webservices/person/header
		Headers: X-API-VERSION = 2 
4- Versioning with Accept
	Example v1: /webservices/person/produces
		Headers: Accept = application/vnd.company.app-v1+json
	Example v2: /webservices/person/produces
		Headers: Accept = application/vnd.company.app-v2+json

#To take into account
Which type of versioning is better? You have to take into account several things, such as:
 - URI pollution: in the 1st and 2nd way, versioning depends on the URI so if it is a complex URI or it is full of information, 3rd and 4th way are better.
 - Misuse of HTTP Headers: 3rd and 4th way of versioning are based on Headers, so you must consider it to decide between 1st/2nd and 3rd/4th.
 - Caching: it's complicated in 3rd and 4th.
 - Can we execute the request on the browser? If so, you must choose between 1st and 2nd because to modify headers you need a rest client (such as postman).
 - API Documentation. It's easier in 1st/2nd than 3rd/4th.
 
#Test
Test with MockMvc and reactive WebTestClient