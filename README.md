# Best Travel

## Description

This project is a demo of a travel agency application. It is composed of some microservices:

- besttravel-front-bff: the front-end of the application and the BFF (Backend For Frontend) for the other microservices.
  It's not the focus of this project, but it's useful to have a global view of the application. It's a Quarkus
  application with a Vaadin UI. All events are sent to and received from Kafka.

- besttravel-orchestrator-service: this is the microservice that routes quote and booking requests from the BFF to
  the appropriate domain services.

- besttravel-(flights|accommodations|car-rental)-service: these are the microservices that manage the flights,
  accommodations and car rentals requests. They are Quarkus applications that process events from Kafka, send these
  requests to the corresponding external services, process the responses and send the results to Kafka.

- besttravel-booking-service: this is the microservice that manages the bookings. It is a Quarkus application that
  process events from Kafka, store booking information in a "database" (in memory) and make this information available
  to the other microservices (not implemented).

- external-company: this is a mock service that represents all external companies that provide flights, accommodations
  and car rentals services. It just responds with random prices and CONFIRMED status to all requests.

## Architecture

```mermaid
flowchart TD
    User(["User"])
    BFF["Front / BFF"]

    subgraph Kafka
        QC([quote-commands])
        BC([booking-commands])
        FQR([flight-quote-requests])
        AQR([accommodation-quote-requests])
        CQR([car-rental-quote-requests])
        FBR([flight-booking-requests])
        ABR([accommodation-booking-requests])
        CBR([car-rental-booking-requests])
        QR([quote-responses])
        BR([booking-responses])
    end

    subgraph Services
        Orch["orchestrator-service"]
        Flights["flights-service"]
        Acc["accommodations-service"]
        Car["car-rental-service"]
        Book["booking-service"]
    end

    Ext[("External\nCompanies APIs")]
    DB[("Storage")]

    User --> BFF
    BFF --> QC & BC
    QC & BC --> Orch
    BC --> Book
    Orch --> FQR & AQR & CQR & FBR & ABR & CBR
    FQR & FBR --> Flights
    AQR & ABR --> Acc
    CQR & CBR --> Car
    Flights & Acc & Car --> QR & BR
    QR & BR --> BFF
    Flights & Acc & Car --> Ext
    Book --> DB
```

## Technologies

- Java 25
- Quarkus 3.34.6
- Kafka (Confluent 7.8.0 / Kafka 3.8.x)
- Vaadin 25
- Docker

## Running the application in dev mode

You can run this application in dev mode that enables live coding using (run each command in a separate terminal):

```shell script
docker-compose up -d
cd besttravel-front-bff && ./mvnw compile quarkus:dev
cd besttravel-orchestrator-service && ./mvnw compile quarkus:dev
cd besttravel-flights-service && ./mvnw compile quarkus:dev
cd besttravel-accommodations-service && ./mvnw compile quarkus:dev
cd besttravel-car-rental-service && ./mvnw compile quarkus:dev
cd besttravel-booking-service && ./mvnw compile quarkus:dev
cd external-company && ./mvnw compile quarkus:dev
```

Open http://localhost:8080 to see the application.

You can see all activities in the logs of each application. This project provides tracking by open telemetry. You can
see the traces in the Jaeger UI: http://localhost:16686

## Running with `docker-compose`

Alternativelly, you can run all applications of this project with a single `docker-compose` execution:

```shell script
docker-compose -f docker-compose-full.yml build --no-cache # just if you want to force a full rebuild
docker-compose -f docker-compose-full.yml up
```

This will build all modules/applications of this project and its dependencies.

Open http://localhost:8080 to see the application.

You can see all activities in the logs of each application. This project provides tracking by open telemetry. You can
see the traces in the Jaeger UI: http://localhost:16686

## Error handling

Domain services (flights, accommodations, car-rental) implement fault tolerance for external REST calls:

- Retry with exponential backoff: 3 attempts with increasing delays (starts at 500ms, doubles each time, caps at
  10s).
- Dead Letter Queue: failed messages are routed to a DLQ for later analysis.
- DLQ monitoring: each service monitors its DLQ and logs failure reasons.

## Next steps

- Implement the booking service with a real database.
- Implement Unit and Integration tests.
- Implement a circuit breaker.
- Implement centralized logging.
- Implement a protocol schema for Kafka messages.
