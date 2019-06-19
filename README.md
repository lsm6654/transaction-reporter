# Fininacial-Transaction-Reporter
<br><br>

# Environment


## Kafka 셋팅
수동 셋팅을 해도 되고, 아래 docker-compose로 구성해도 된다. 편한 방식대로 사용할 것.
<br><br>

### docker-compose

<h4> Prerequisites </h4>
<ul>
    <li> docker </li>
    <li> docker-compose </li>
</ul>
<br>

<h4> Setup </h4>

$ docker-compose up -d
<br><br>

<h4> producer/consumer console command for test </h5>

$ docker exec -it transaction-reporter-kafka /opt/kafka/bin/kafka-console-producer.sh --broker-list 127.0.0.1:9092 --topic ${TOPIC_NAME}

$ docker exec -it transaction-reporter-kafka /opt/kafka/bin/kafka-console-consumer.sh --bootstrap-server 127.0.0.1:9092 --topic ${TOPIC_NAME}
<br><br>

<h4> start/stop command </h4>

$ docker ps -a

$ docker stop ${위 명령어에서 PROCESS_NAME}

$ docker start ${PROCESS_NAME}
<br><br>