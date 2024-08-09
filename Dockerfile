FROM amazoncorretto:21-alpine-jdk
ADD target/contact-manager.jar tmp/contact-manager.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/tmp/contact-manager.jar"]
