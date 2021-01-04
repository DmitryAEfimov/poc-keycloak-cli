### Build Java Application image ###
FROM maven:3.6.3-jdk-11 as builder

WORKDIR /tmp/poc-cli

COPY src ./src
COPY pom.xml .

RUN mvn clean install

### Run Java Application image ###
FROM adoptopenjdk:11-jre-openj9
MAINTAINER  Dmitry Efimov <dmitry.a.efimov@gmail.com>

EXPOSE 8080

WORKDIR /opt/app
COPY --from=builder /tmp/poc-cli/target/client-app-0.1.jar .

RUN mkdir ./logs
RUN groupadd -g 999 appuser && \
    useradd -r -u 999 -g appuser appuser
RUN chown -R appuser:999 /opt/app/ && chmod 644 client-app-0.1.jar
USER appuser

ENV KEYCLOAK_PUBLIC_CLI=false
CMD java \
    -Dkeycloak.auth-server-url=${KEYCLOAK_AUTH_URL} \
    -Dkeycloak.realm=${KEYCLOAK_REALM_NAME} \
    -Dkeycloak.resource=${KEYCLOAK_CLI} \
    -Dkeycloak.credentials.secret=${KEYCLOAK_CLI_SECRET} \
    -Dkeycloak.public-client=${KEYCLOAK_PUBLIC_CLI} \
    -Dspring.datasource.url=${DB_URL} \
    -Dspring.datasource.username=${DB_USERNAME} \
    -Dspring.datasource.password=${DB_PASSWORD} \
    -jar client-app-0.1.jar
