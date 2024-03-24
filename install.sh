#!/bin/bash

./terminate.sh
# Set the path to the Java executable
JAVA_HOME=/usr/bin/java
export PATH=$JAVA_HOME/bin:$PATH

# Set the path to the JAR files
java -jar discovery-server/target/discovery-server-1.0.0-SNAPSHOT.jar &
java -jar api-gateway/target/api-gateway-1.0.0-SNAPSHOT.jar &
java -jar security-service/target/security-service-1.0.0-SNAPSHOT.jar &
java -jar cart-service/target/cart-service-1.0.0-SNAPSHOT.jar &
java -jar product-service/target/product-service-1.0.0-SNAPSHOT.jar &
java -jar user-service/target/user-service-1.0.0-SNAPSHOT.jar &
java -jar order-service/target/order-service-1.0.0-SNAPSHOT.jar &
java -jar media-service/target/media-service-1.0.0-SNAPSHOT.jar &
java -jar carousel-service/target/carousel-service-1.0.0-SNAPSHOT.jar &
java -jar blog-service/target/blog-service-1.0.0-SNAPSHOT.jar &

# Wait for all processes to finish
wait