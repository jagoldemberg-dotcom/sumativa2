# ===== Build =====
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn -q -DskipTests clean package


# ===== Run =====
FROM eclipse-temurin:21-jre
ENV JAVA_OPTS="-XX:+UseContainerSupport -XX:MaxRAMPercentage=75"
WORKDIR /opt/app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 8080
USER 1000
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]