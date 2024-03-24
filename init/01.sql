CREATE DATABASE IF NOT EXISTS `cozastore_order_service`;
CREATE DATABASE IF NOT EXISTS `cozastore_security_service`;
CREATE USER 'root'@'localhost' IDENTIFIED BY '618619';
GRANT ALL PRIVILEGES ON `cozastore_order_service`.* TO 'root'@'%';
GRANT ALL PRIVILEGES ON `cozastore_security_service`.* TO 'root'@'%';