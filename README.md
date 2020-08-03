# Customer Statistics Web API

This is a simple Java application for processing customers requests (POST) and retrieving statistical data (GET).

## Tech Stack

Programming Language: Java 8

Frameworks: Maven, Spring Boot
 
DataBase: H2

Tools: Eclipse, Spring Boot (Spring Initializr), GIT, Fiddler Web Debugger

## Installation

There is no need to install this program. You can import, run and debug this it with Eclipse IDE for Enterprise Java Developers, but you can also execute the provided .jar file.

For executing it in the command line:

1- go to the directory where the file CustomerStatsAPI.jar is placed

2- use the command java -jar CustomerStatsAPI.jar

## Usage


When running the application you can access the database by the link:

http://localhost:8080/h2/

The url/connection string for that is: 

jdbc:h2:mem:TEST;DB_CLOSE_DELAY=-1


You can use Fiddler for your GET and POST requests.

For the POST requests you can use the URL:
http://localhost:8080/stats

And a request body:
{"customerID":1,"tagID":2,"userID":"abcd","remoteIP":"1550","timestamp":1500000000}

The GET request should have a format as:

http://localhost:8080/stats/1/2020-08-03

And no request body should be passed.

## Resources:  

https://spring.io/guides/tutorials/bookmarks/
https://github.com/iamvickyav/spring-boot-data-H2-embedded
https://www.h2database.com/html/main.html
https://mkyong.com/spring/spring-jdbctemplate-querying-examples/
https://dzone.com/articles/running-on-time-with-springs-scheduled-tasks
https://www.geeksforgeeks.org/java-program-to-count-the-occurrence-of-each-character-in-a-string-using-hashmap/
