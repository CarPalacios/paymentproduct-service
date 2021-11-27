FROM openjdk:11
COPY "./target/paymentproduct-service-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8095
ENTRYPOINT ["java","-jar","app.jar"]