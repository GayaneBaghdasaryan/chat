# chat

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/chat-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.
## Event Streaming Platform: Apache Kafka

This project utilizes **Apache Kafka** as its event streaming platform. The key reasons for this choice include:

- **Scalability:** Kafka is designed to handle high-throughput and real-time data streams, making it ideal for applications that expect to scale in the future.
- **Fault Tolerance:** With its distributed architecture and replication features, Kafka ensures reliable delivery of messages even in the event of server failures.
- **Ecosystem and Community Support:** Kafka has a mature ecosystem, extensive tooling, and a large community, which simplifies integration and provides long-term support.
- **Low Latency:** Kafka provides low-latency message delivery, which is critical for real-time processing and event-driven architectures.
- **Integration with Microservices:** Kafka’s design makes it an excellent fit for microservice architectures, enabling decoupled, asynchronous communication between services.

By leveraging Apache Kafka, the project can efficiently publish and consume events related to group operations, ensuring that any changes (such as a new group search or update) can be propagated to other system components or services in near real time.
