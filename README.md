# Microservice-cozastore

## Tentative technologies and frameworks

- Java 17
- Spring boot 3
- RabbitMQ
- GitHub Actions
- OpenTelemetry
- Redis, RabbitMQ
- Grafana, Loki, Prometheus, Tempo

## Run this project

### Build this service:

`mvn clean package`

## Start docker and services:

#### Also, make sure that the `install.sh` file has execute permissions. You can set the execute permission using the `chmod` command:

`chmod +x install.sh`

```bash
docker-compose -f docker-compose.yml -p docker up -d

./install.sh
```
## Stop services:

#### Also, make sure that the `terminate.sh` file has execute permissions. You can set the execute permission using the `chmod` command:

`chmod +x terminate.sh`

```bash
./terminate.sh
```

## Contributing
- Attention ! this project is for learning mirco-service, only !
