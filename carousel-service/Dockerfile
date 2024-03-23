FROM eclipse-temurin:17-jre-alpine
MAINTAINER cazastore
RUN apk add --no-cache bash
ARG JAR_FILE
COPY target/carousel-service-0.0.1-SNAPSHOT.jar carousel-service-0.0.1.jar
COPY wait-for-it.sh wait-for-it.sh
RUN chmod +x wait-for-it.sh
ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "carousel-service-1.0.0.jar"]