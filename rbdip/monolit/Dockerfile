FROM openjdk:21
COPY build/libs/monolit-1.0-SNAPSHOT.jar monolit-1.0-SNAPSHOT.jar

ENTRYPOINT ["sh", "-c", "sleep 50 && java -jar monolit-1.0-SNAPSHOT.jar"]