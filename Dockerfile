FROM openjdk:22-jdk
ADD target/contact-manager.jar tmp/contact-manager.jar
ENTRYPOINT ["java","-jar","/tmp/contact-manager.jar"]
