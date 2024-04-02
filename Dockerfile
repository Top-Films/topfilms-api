FROM --platform=linux/arm64 amazoncorretto:21-alpine3.18

USER root

WORKDIR /app

EXPOSE 8080

COPY target/*.jar ./topfilms.jar

RUN chown -R 1000:1000 /app

USER 1000

CMD ["java", \
    "-Dspring.profiles.active=prod", \
    "-Dserver.port=8080", \
    "-jar", \
    "topfilms.jar"]