# Online analytic service

## Overview
This app build on _**Spring Boot**_, used _**PostgreSQL**_ and _**Redis**_ (for cache purpose) and _**Apache Kafka**_ to 
transport fetched data.

Also for data processing there was used _**Spark**_ (v2.3.1).

#### _It is multi-container application_, which includes the following services:
- _**ZooKeeper**_ as cluster manager for Apache Kafka.

- _**Kafka**_ as distributed streaming service with predefined topics `onlineStream` and `urlCounterStream`.
- _**Redis**_ for cache purpose to fetch only new data from target resources.
- _**pgAdmin**_ service to check data in PostgreSQL. You can access it on `localhost:80` with the following credentials:
    - `user`: _nick_
    - `password`: _password_
    - `db name`:  _rss_itemdb_ or _resourcedb_
    - `port`: _5432_
    - `host`: to get host exec `docker inspect postgres` and copy container IP.

##### and the following custom images for:

- _**Postgres**_ with predefined databases `resourcedb` and `rss_itemdb` to store fetched data. 

- _**Producer**_ service which parse RSS resources and sends messages to Kafka (used `KafkaTemplate`). 
This service connected with _Redis for cache last fetched data_. The process of fetching data from resources
organized in _**async manner**_.
- _**Writer**_ service receives the messages from Kafka and save it to PostgreSQL 
(used `@KafkaListener` for predefined topic `onlineStream`).
- _**Spark**_ service receives data from _Kafka_ by topic `onlineStream`, transform it to aggregated data grouped 
by domain and finally send it to Kafka topic `urlCounterStream`. _Also you can see this sorted data on terminal_ 
where you started this app. **So there used pipeline _Kafka -- Spark -- Kafka_**.
- _**Listener**_ service receives the messages fromKafka and send it by websocket connection to client 
(only for test purpose  to show results). Messages received by topic `urlCounterStream` and contain aggregated 
information about counts of news by each domain.
- _**Client**_ service build on Angular on which we can see the program results (the count of fetched data
from each domain) _in real-time by websocket connection_ with listener service. You can access it on `localhost:4200`.

**To link services (containers) was used Docker Compose.**

#### All configuration to start up project are in:
 - `docker-compose.yml` file (prod mode)
 - `docker-compose-dev.yml` file (dev mode)

## Prerequisites (for dev mode)
- Provide actual absolute path to your `.m2` directory on host machine in `docker-compose-dev.yml` file.

- Enter to `microservice` folder and run `mvn clean install` to install parent pom for our microservices.
- **:exclamation:IMPORTANTexclamation::** Enter to `postgres` folder and modify `init.sh`:
 select _**End of Line Sequence**_ from `CRLF` to `LF`. If you don't do that this script failed when you run docker
 because Linux don't recognize `CRLF` end line sequence.

## How to start app?
1) download project `git clone https://github.com/NickRbk/Monitoring-service-docker.git`
2) enter to downloaded folder:

    - you can run app from pre-build images on Docker Hub by `docker-compose up` (prod mode)

    - or follow the above prerequisites and run app in dev mode `docker-compose -f docker-compose-dev.yml up`.

:exclamation: _**On some reasons to start this application you will need to increase limit of resources available for Docker**_.

After application start up go to `localhost:4200` to monitor current work (it may takes a couple of minutes 
to up all containers, watch the progress on terminal).

When you enter by this link you establish websocket connection with listener service and will get information 
from listener service (on screen should be `WebSocket connection STATUS: OK`). 

After start in _**2 minutes**_ `producer service` will start to check resources from DB (sample data of which service we 
monitor was pre-populated) with schedule _**every 5 minutes**_.

**To shut down app use `docker-compose down` or `docker-compose -f docker-compose-dev.yml down`**.