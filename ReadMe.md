Simple Spring Boot Project Setup without IDE:

1) Download java 21 Semeru from https://developer.ibm.com/languages/java/semeru-runtimes/downloads/ install and check java -- version for correct installation.
2) Download and install Maven from https://maven.apache.org/download.cgi C:\Program Files\Apache\Maven\apache-maven-3.9.11
set MAVEN_HOME = C:\Program Files\Apache\Maven\apache-maven-3.9.11 and check installation done perfectly mvn -version
3) Got to https://start.spring.io/
Configure:

Project: Maven

Language: Java

Spring Boot Version: 3.3.x (latest stable)

Group: com.example

Artifact: springbootdemo

Java: 21

Dependencies: Spring Web
Or you can start with the same files provided in the project point 3 becomes optional in this scenario
4) Put src and pom.xml in parent folder and run "mvn clean package"
5) now run the jar using "java -jar target\jar name" from the target folder or run by "mvn spring-boot:run" from the project folder
6) Service will start running at http://localhost:8080/
