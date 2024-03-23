#!/bin/bash

./terminate.sh
# Set the path to the Java executable
JAVA_HOME=/usr/bin/java
export PATH=$JAVA_HOME/bin:$PATH

# Set the path to the JAR files
java -jar discovery-server/target/discovery-server-0.0.1-SNAPSHOT.jar &
java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar &
java -jar security-service/target/security-service-0.0.1-SNAPSHOT.jar &
java -jar cart-service/target/cart-service-0.0.1-SNAPSHOT.jar &
java -jar product-service/target/product-service-0.0.1-SNAPSHOT.jar &
java -jar user-service/target/user-service-0.0.1-SNAPSHOT.jar &
java -jar order-service/target/order-service-0.0.1-SNAPSHOT.jar &
java -jar media-service/target/media-service-0.0.1-SNAPSHOT.jar &
java -jar carousel-service/target/carousel-service-0.0.1-SNAPSHOT.jar &
java -jar blog-service/target/blog-service-0.0.1-SNAPSHOT.jar &

# Wait for all processes to finish
wait