FROM java:8-jdk-alpine
COPY ./target/camel-demo-0.0.6-SNAPSHOT.jar /usr/app/
WORKDIR /usr/app
RUN sh -c 'touch camel-demo-0.0.6-SNAPSHOT.jar'
ENTRYPOINT ["java","-jar","camel-demo-0.0.6-SNAPSHOT.jar"]