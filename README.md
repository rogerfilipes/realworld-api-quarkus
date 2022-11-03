# ![RealWorld Example App](logo.png)

 ### [Quarkus Framework](https://quarkus.io/) codebase containing real world examples (CRUD, auth, advanced patterns, etc) that adheres to the [RealWorld](https://github.com/gothinkster/realworld) spec and API.


### [Demo](https://demo.realworld.io/)&nbsp;&nbsp;&nbsp;&nbsp;[RealWorld](https://github.com/gothinkster/realworld)


This codebase was created to demonstrate a fully fledged fullstack application built with [Quarkus Framework](https://quarkus.io/)  including CRUD operations, authentication, routing, pagination, and more.

We've gone to great lengths to adhere to the [Quarkus Framework](https://quarkus.io/)  community styleguides & best practices.

For more information on how to this works with other frontends/backends, head over to the [RealWorld](https://github.com/gothinkster/realworld) repo.


# How it works

The application uses Quarkus with the following technologies/modules
 * [RESTEasy Reactive](https://quarkus.io/guides/resteasy-reactive)
 * [Smallrye OpenAPI](https://quarkus.io/guides/openapi-swaggerui)
 * [jOOQ](https://www.jooq.org/)
 * [Liquibase](https://www.liquibase.org/)
 * [PostgreSQL](https://www.postgresql.org/)
 * [MapStruct](https://mapstruct.org/)
 * [Testcontainers](https://www.testcontainers.org/)
 * [Smallrye-JWT](https://quarkus.io/guides/security-jwt)

Code organization:
```
|-- application                 -> business orchestration layer and api endpoint
        |-- models              -> models used for rest requests/responses
        |-- mapper              -> mappers to translate between domain and model
|-- domain                      -> domain services 
        |-- <domain object>
            |-- model           
            |-- <domain object>Service
 |-- infrastructure             -> Configurations and technical details
```

# Database and model entities generation
This application uses PostgresSQL to store the data, the connection string and credentials should be located at application.properties.

The model generation for jooq uses the same strategy explained on this article https://jasondl.ee/2022/quarkus-dev-services-jooq-flyway-testcontainers-full-example. 
In short, it uses testcontainers to boot a postgres instance, apply the liquibase scripts and then generated the jooq entities.

The jooq entities are already committed, but if you wish to modify something, change the liquibase script and run
```shell
mvn -P codegen compile -DskipTests
```

The generated entities will be located in ```infrastructure/repository/jooq```

For development purposes the application uses DevServices.

# Getting started

### Clone the repo and run 
```shell
mvn wrapper:wrapper 
```


### To start local instance 
```shell
 ./mvnw compile quarkus:dev
 ```

* The application endpoint http://localhost:8080
* Swagger UI http://localhost:8080/q/swagger-ui/
* Quarkus Dev http://localhost:8080/q/dev


### Building jar file

```shell
./mvnw package
```
