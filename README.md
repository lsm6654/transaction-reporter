# Fininacial-Transaction-Reporter
<br>

## Build & Run

```
$ ./mvnw clean package dependency:copy-dependencies

$ java -jar {packing_file}
```

***

## Environment

- java 1.8
- kafka 2.1

***

### Kafka 셋팅
수동 셋팅을 해도 되고, 아래 docker-compose로 구성해도 된다. 편한 방식대로 사용할 것.

#### docker-compose

##### Prerequisites
<ul>
    <li> docker </li>
    <li> docker-compose </li>
</ul>

##### Setup

```
$ docker-compose up -d
```

##### producer/consumer console command for test
```
$ docker exec -it transaction-reporter-kafka /opt/kafka/bin/kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic ${TOPIC_NAME}

$ docker exec -it transaction-reporter-kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic ${TOPIC_NAME}
```

##### start/stop command 
```
$ docker ps -a

$ docker stop ${위 명령어에서 PROCESS_NAME}

$ docker start ${PROCESS_NAME}
```

***
