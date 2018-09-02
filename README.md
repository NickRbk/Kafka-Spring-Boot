# Online analytic service

## Overview
This app build on Spring Boot, used PostgreSQL and Redis (for cache purpose) and Apache Kafka to process fetched data. 

_It is multi-container application_, the dev version of which includes the following services:
1. _**ZooKeeper**_ as kluster manager for Apache Kafka.
2. _**Apache Kafka**_ with predefined topic 'onlineStream'.
3. _**Postgres**_ to store fetched data.
4. _**pgAdmin**_ service to check data in PostgreSQL. You can access it on `localhost:80`.
5. _**Redis**_ for cache and fetching only new data from target resources.
6. _**Producer**_ parse RSS resources and sends messages to Kafka (used `KafkaTemplate`). 
This service connected with _Redis for cache last fetched data_. The process of fetching data from resources
organized in _async manner_.
7. _**Writer**_ receives the messages from Kafka and save it to PostgreSQL (used `@KafkaListener` for predefined topic 'onlineStream').
8. _**Listener**_ receives the messages fromKafka and send it by websocket connection to client (only for test purpose).
9. _**Client**_ service build on Angular on which we can see the programm results (the count of fetched data from RSS)
in real-time by websocket connection with listener service. You can access it on `localhost:4200`.

To link services (containers) used Docker Compose.

All configuration to start up project are in `docker-compose.yml` file.

## Prerequesits
- Provide actual absolute path to your `.m2` directory on host machine in `docker-compose.yml` file.
- Enter to `microservice` folder and run `mvn install` to install parrent pom for our microservices.
- Enter to `client` folder and run `yarn install` to install dependencies for frontend side.
- **IMPORTANT:** Enter to `postgres` folder and modify `init.sh`:
 select _**End of Line Sequence**_ from `CRLF` to `LF`. If you don't do that this script failed when you run docker
 because Linux don't recognize `CRLF` end line sequence.

 ## How to start app?
1) download project `git clone https://github.com/NickRbk/Monitoring-service-docker.git`
2) follow prerequesits
2) enter to downloaded folder and run `docker-compose up --build`

After application start up go to `localhost:4200` to monitor current work (it may takes a couple of minutes to up all containers).
When you enter by this link you establish websocket connection with listener service and will get information about countes of items
we received from RSS (on screen should be `WebSocket connection STATUS: OK`). 

After start in 3 minutes workers will start to check resources from DB (sample data prepopulated) with schedule every 5 minutes.

**To shut down app use `docker-compose down`**.