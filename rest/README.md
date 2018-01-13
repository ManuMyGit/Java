# This is a collection of RESTful API examples.
In order to do a complete RESTful API you need to have several things into account:
- A RESTful API must be stateless.
- You must "see" your entities as resources.
    - Each resource has a URI (Uniform Resource Identifier).
    - Each resource can have different representations (for example, JSON or XML). See example [Content negotiation example](https://github.com/ManuMyGit/Java/tree/master/rest/contentnegotiation) to see how to retrieve either JSON or XML in Spring automatically.
    - The URI must not specify the action (see below).
- Rest is based on Http, so make best use of Http.
    - Http methods to specify actions: GET, POST, PUT, DELETE.
    - Http responses: 
        - 200 - SUCCESS
        - 201 - CREATED
        - 400 - BAD REQUEST
        - 401 - UNAUTHORIZED
        - 404 - RESOURCE NOT FOUND
        - 500 - SERVER ERROR
- URI best practices:
    - Secure info must not be present in URI.
    - Use plurals to identify each resource.
    - Use noun from resources (neither actions nor verbs).
- Use versioning to expose your different versions of your API (see example [Version example](https://github.com/ManuMyGit/Java/tree/master/rest/versioningservice) of how to version a API).
- Use HATEOAS as long as you can (and you need).
    - Each request is recommended to get all information needed. A good example for this is Facebook. When you see a picture, you can like, dislike or hate the picture. The first request must get all links to do this actions (see example [HATEOAS example](https://github.com/ManuMyGit/Java/tree/master/rest/jpacrudrestapi) of how to implement HATEOAS in Spring. In that example, you'll be able to see how to use Spring Boot JPA as well).
- There is no a standard API documentation, but it is totally recommended to document your API (see example [Documentation example](https://github.com/ManuMyGit/Java/tree/master/rest/apidocument) of how to document a API with Swagger).
- Take into account if you need to secure your API (see example [Security example](https://github.com/ManuMyGit/Java/tree/master/rest/secutiry) of how to secure a API with Spring Security).
- It is a good practice to handle exceptions to return a estructured error or the error you want (see [Handler Exceptions example](https://github.com/ManuMyGit/Java/tree/master/rest/handlerexceptions) of how to handle exceptions in Spring).

# Other examples
Eventually, in this section you'll be able to find others examples:
- [Internationalization](https://github.com/ManuMyGit/Java/tree/master/rest/internationalization). In this example you can see how to implement internationalization with Spring in a RESTful API.
