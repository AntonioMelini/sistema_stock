FROM eclipse-temurin:17-jdk
COPY target/sistema_stock-0.0.1-SNAPSHOT.jar java-app.jar
ENTRYPOINT ["java","-jar","java-app.jar"]