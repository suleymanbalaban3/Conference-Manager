FROM java:8-jdk-alpine

COPY ./target/managing-conference-0.0.1-SNAPSHOT.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch managing-conference-0.0.1-SNAPSHOT.jar'

ENTRYPOINT ["java","-jar","managing-conference-0.0.1-SNAPSHOT.jar"]