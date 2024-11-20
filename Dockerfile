FROM openjdk:17
#VOLUME /tmp
COPY build/libs/MP-*SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

#ADD /build/libs/demo1-*.jar /demo1.jar
#
#ENTRYPOINT ["java", "-jar", "/demo1.jar"]

EXPOSE 2437



