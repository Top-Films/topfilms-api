FROM --platform=linux/arm64 amazoncorretto:21-alpine3.18

ARG ENV

USER root
WORKDIR /app

COPY target/*.jar ./topfilms.jar
RUN chown -R 1000:1000 /app

USER 1000
EXPOSE 8080
CMD ["java", \
    "-Dspring.profiles.active=$ENV", \
    "-Dserver.port=8080", \
    "-Dspring.application.name=topfilms-api-$ENV", \
    "-jar", \
    "topfilms.jar"]