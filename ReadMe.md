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

Installation and excution on linux distribution 
1. sudo apt update && sudo apt upgrade -y
2. sudo apt install openjdk-17-jdk -y
3. and then start the app in no hup so it is available after you close terminal
nohup java -jar application-0.0.1-SNAPSHOT.jar > app.log 2>&1 &

Steps to Generate SSH keys from GCP to connect VM with WinSCP and/or PUTTY
1. Run below command in the machine, on the VM you want to connect
ssh-keygen -t rsa -b 2048 -f ~/.ssh/mykey
2. Keep putting enter to let it run with default params :
do cat ~/.ssh/mykey.pub
copy content and remove @machine name and paste it in GCP Compute Engine Metadata add or create ssh key
3. run command cat ~/.ssh/mykey and copy content and save as mykey.pem
Load this file in PuTTygen 
Open PuTTYgen
Load → mykey.pem
Save private key → mykey.ppk
4. now you can connect by using public ip on port 22 with username in PuTTy