# Appliance Control
Backend service (REST API) to control an appliance such as a wash machine or an oven.

*Note:* For now there is only oven control.

##Build and run
In root folder of project, execute:
```cmd
mvn clean install 
java -jar target\appliancecontrol-0.0.1-SNAPSHOT.jar
```



## H2 database
When app run you can check database state through web console: [http://localhost:8080/h2](http://localhost:8080/h2)
```cmd
JDBC URL: jdbc:h2:mem:test
User Name: sa 
(without password)
```
On start there is already test data from *data.sql*.