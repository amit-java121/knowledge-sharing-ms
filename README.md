# Knowledge Sharing API

This project basically interacts with the database to get the details of the links of TED talks, Modify the details,
Insert new details, Delete the existing ones (CRUD operations).

## Requirement

The project need to expose 4 endpoints, that can talk to database layer.

| OPERATION | ENDPOINT                | DESCRIPTION                                                                                                  |
| --------- |---------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------|
| GET | /knowledge-sharing      | Endpoint fetches the required data based on the input variables (author, title, views, likes)                |
| POST | /knowledge-sharing      | Endpoint inserts the new records based on the data from external provider                                    |
| PUT | /knowledge-sharing/{id} | Endpoint updates the existing data based on the input data that has to be changed with the unique identifier |
| DELETE | /knowledge-sharing/{id} | Endpoint deletes the existing data based on the unique identifier passed from the external provider          | 

## Skill set used

- Java 11
- Spring
- POSTGRES Database
- Liquibase
- Testcontainers
- Docker
- Scripting
- Test Driven Development

## How to start the project?

Since this project uses the postgres instance to support our operations.

- Need to start the postgres instance on your local.

> start the docker instance of the POSTGRES in the docker-compose file provided in the path
> ./scripts/db/docker-compose.yml (starts instance in port 9001) --> localhost:9001

- Liquibase Migrations

Liquibase allows you to specify the database change you want using SQL or several different database-agnostic formats,
including XML, YAML, and JSON. Developers can abstract the database code to make it extremely easy to push out changes
to different database types.

- Need to run the Liquibase migration script

> In the path ./script/db/liquibase-update-local.sh | run the script

by running the above script creates the actual DB changes defined in src/main/resources/db/changelog is replicated in the dockerized
postgres instance that is created.

> to verify: use your local database GUI (DBeaver, SQL Developer)<br/>
> DB URL: "jdbc:postgresql://localhost:9001/db-knowledge-sharing" <br/>
> DB USER: "dbkwnsha"<br/>
> DB PASSWORD: "dbkwnsha"<br/>

> :warning: **Anytime your free to delete the DB created and redo the migration steps**

## WE ARE READY?

let's start the microservice project, so by default it will start on the port localhost:8080 now!

> Now with the shared POSTMAN collection at the path **./postman/Knowledge-Sharing API.postman_collection.json**<br/>
> Import the postman collection on the local postman app on the PC. We are free to try the endpoints one by one.

## Swagger API

> Access Swagger API page on the URL **http://localhost:8080/swagger-ui.html**

## Role Based Authentication

> username passed a header parameter with header name: (as an example - here)<br />
>  UUID values created from the Java library (java.util.UUID)<br />
> "x-user-id" --> a207644e-d040-4448-901f-16fd6374fb0c (CUSTOMER)<br />
> "x-user-id" --> 31400124-6acd-4fcc-88be-f2a24c769ef0 (ADMIN)<br />
> To call the service from the POSTMAN (Updated the postman collection too with the required UUID mentioned above)

## Enhancements

> - **Loading the File through streams** <br />For loading a large set of files into the DB, like initial loading or
    future loading of massive data, we can run the files based on the streaming. where the microservice will be
    listening to a topic in KAFKA which will iterate and update the database in a async way
> - Another way is everytime run a LIQUIBASE migration to load the data.


