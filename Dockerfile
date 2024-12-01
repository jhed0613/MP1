FROM openjdk:17
COPY build/libs/app.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 2437

#ADD /build/libs/demo1-*.jar /demo1.jar
#
#ENTRYPOINT ["java", "-jar", "/demo1.jar"]