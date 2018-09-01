# Online analytic service

## Overview
This app build on Spring Boot, used PostgreSQL and Redis (for cache purpose) and Apache Kafka to process fetched data. 

_It is multi-container application_, the dev version of which includes the following services:
1. _**ZooKeeper**_ as kluster manager for Apache Kafka.
2. _**Apache Kafka**_ with predefined topic 'onlineStream'.
3. _**Postgres**_ to store fetched data.
4. _**pgAdmin**_ service to check data in PostgreSQL.
5. _**Redis**_ for cache and fetching only new data from target resources.
6. _**Producer**_ parse RSS resources and sends messages to Kafka (used `KafkaTemplate`). 
This service connected with _Redis for cache last fetched data_. 
7. _**Writer**_ receives the messages from Kafka and save it to PostgreSQL (used `@KafkaListener` for predefined topic 'onlineStream').
The process of fetching data from resources organized in _async manner_.
8. _**Listener**_ receives the messages fromKafka and send it by websocket connection to client (only for test purpose).
9. _**Client**_ service build on Angular on which we can see the programm results (the count of fetched data from RSS)
in real-time by websocket connection with listener service.

To link services (containers) used Docker Compose.

All configuration to start up project are in `docker-compose.yml` file.

## Prerequesits
- Provide actual absolute path to your `.m2` directory on host machine in `docker-compose.yml` file.
- Enter to `microservice` folder and run `mvn install` to install parrent pom for our microservices.
- **IMPORTANT:** Enter to `postgres` folder and modify `init.sh`:
 select _**End of Line Sequence**_ from `CRLF` to `LF`. If you don't do that this script failed when you run docker
 because Linux don't recognize `CRLF` end line sequence.

 ## How to start app?
1) download project `git clone https://github.com/NickRbk/Monitoring-service-docker.git`
2) follow prerequesits
2) enter to downloaded folder and run `docker-compose up`

**To shut down app use `docker-compose down`**