FROM mcr.microsoft.com/openjdk/jdk:21-ubuntu AS build
#FROM amazoncorretto:17-alpine AS build

WORKDIR /build

COPY . /build

RUN ./mvnw package -Pproduction


FROM registry.access.redhat.com/ubi8/openjdk-17:1.15

ENV LANGUAGE='en_US:en'
ENV KAFKA_BOOTSTRAP_SERVERS=""

COPY --chown=185 --from=build /build/target/quarkus-app/lib/ /deployments/lib/
COPY --chown=185 --from=build /build/target/quarkus-app/*.jar /deployments/
COPY --chown=185 --from=build /build/target/quarkus-app/app/ /deployments/app/
COPY --chown=185 --from=build /build/target/quarkus-app/quarkus/ /deployments/quarkus/

EXPOSE 8080
USER 185
ENV JAVA_OPTS="-Dquarkus.http.host=0.0.0.0 -Djava.util.logging.manager=org.jboss.logmanager.LogManager"
ENV JAVA_APP_JAR="/deployments/quarkus-run.jar"

