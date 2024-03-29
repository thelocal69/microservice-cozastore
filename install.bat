@echo off

call terminate.bat
:: Set the path to the Java executable
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_291
set PATH=%JAVA_HOME%\bin;%PATH%

:: Set the path to the JAR files
start java -jar discovery-server/target/discovery-server-0.0.1-SNAPSHOT.jar
start java -jar api-gateway/target/api-gateway-0.0.1-SNAPSHOT.jar
start java -jar security-service/target/security-service-0.0.1-SNAPSHOT.jar
start java -jar cart-service/target/cart-service-0.0.1-SNAPSHOT.jar
start java -jar product-service/target/product-service-0.0.1-SNAPSHOT.jar
start java -jar user-service/target/user-service-0.0.1-SNAPSHOT.jar
start java -jar order-service/target/order-service-0.0.1-SNAPSHOT.jar
start java -jar media-service/target/media-service-0.0.1-SNAPSHOT.jar
start java -jar carousel-service/target/carousel-service-0.0.1-SNAPSHOT.jar
start java -jar blog-service/target/blog-service-0.0.1-SNAPSHOT.jar

:: Wait for all processes to finish
timeout /t 5