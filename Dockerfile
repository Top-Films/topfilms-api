FROM --platform=linux/arm64 amazoncorretto:21-alpine3.18

ENV DATASOURCE_URL=${DATASOURCE_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

USER root

WORKDIR /app

EXPOSE 8080

COPY target/*.jar ./topfilms.jar

RUN chown -R 1000:1000 /app

USER 1000

CMD ["java", \
    "-Dspring.profiles.active=prod", \
    "-Dserver.port=8080", \
    "-Dspring.datasource.url=$DATASOURCE_URL", \
    "-Dspring.datasource.username=$DB_USERNAME", \
    "-Dspring.datasource.password=$DB_PASSWORD", \
    "-jar", \
    "topfilms.jar"]