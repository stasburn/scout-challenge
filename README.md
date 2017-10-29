### Prerequisites
* Java version 1.8.0_162. Lower versions could not work because of the [issue](http://bugs.java.com/bugdatabase/view_bug.do?bug_id=8170041)
* DynamoDB on http://localhost:8000. If you use Docker: 
```sh
docker run -v "$PWD":/dynamodb_local_db -p 8000:8000 cnadiminti/dynamodb-local:latest
```
If you have your own installation of DynamoDB, please provide endpoint and credentials in [config file](src/main/resources/application.properties)  
### How to build
Project has maven wrapper. Check your JAVA_HOME and run
```sh
./mvnw install
```
It will build and run tests.
_**Note:** running integration[tests](src/test/java/com/autoscout24/storage/AdvertRepositoryIntegrationTest.java)will cleanup table Advert_

### How to run
```sh
./mvnw spring-boot:run
```
 Will run rest service on [http://localhost:8080](http://localhost:8080)

### How to use it
If you familiar with Postman here you can run couple of request again your local install
[![Run in Postman](https://run.pstmn.io/button.svg)](https://app.getpostman.com/run-collection/c66450cb0f316613ae2f)