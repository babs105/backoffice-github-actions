#FROM openjdk:8-jre
#
#VOLUME /tmp
## Add the built jar for docker image building
#ADD target/backofficev2.jar backofficev2.jar
#
## Build a shell script because the ENTRYPOINT command doesn't like using ENV
#RUN echo "#!/bin/bash \n java -jar /backofficev2.jar" > ./entrypoint.sh
#RUN chmod +x ./entrypoint.sh
#
## Run the generated shell script.
#ENTRYPOINT ["./entrypoint.sh"]
#EXPOSE 8080
#

##
FROM openjdk:8
EXPOSE 8080
ADD target/backofficev2.jar backofficev2.jar
ENTRYPOINT ["java","-jar","/backofficev2.jar"]




#FROM openjdk:8
#ADD target/backoffice-0.0.1-SNAPSHOT.jar app.jar
#ENTRYPOINT ["java","-jar","/app.jar"]

#FROM openjdk:8
#EXPOSE 8080
#ADD target/backoffice.jar backoffice.jar
#ENTRYPOINT ["java","-jar","/backoffice.jar"]


#FROM maven:3.8.2-jdk-8
#
#WORKDIR /backofficev2
#COPY . .
#RUN mvn clean install -DskipTests
#
#CMD mvn spring-boot:run