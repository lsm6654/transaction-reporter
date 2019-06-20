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

## 회고

### 좋았던 점
- 간단한 토이 프로젝트 느낌이라 재미있었음.
- 빈 관리
    - 맨날 스프링만 쓰다가 없이 하려니까 가장 먼저 고민했었던 부분. 
    - 먼저 고민하기 잘했던 것 같다. 짧은 시간에 어떻게 만들긴 함..;;
- 테스트 코드
    - Junit 5 도입
    - profiler 모듈에는 단위 테스트를 많이 적용하려 함.
- Kafka
    - exactly once
    - pub/sub scale out 고려
- 설계
    - 고민을 많이 함 -> 조금씩 나아지는 모습 -> 그러나 여전히 어려움
- clean code / OOP
    - 노력한것에 비해 만족스러우나, 최선인지는 모르겠음.


### 아쉬운 점
- 업무외적으로 진행하니 쉽지 않음. 시간도 모자라고 하나에 빠지면 다른걸 좀 제쳐두게 되는...
- 빈 관리
    - 허접하기 짝이 없다. 조금 더 이쁘게 더 편하게 만들려면 어떻게 해야할까? Reflection?
- 테스트 코드
    - profiler를 제외한 모듈에서 단위 테스트를 많이 작성하지 못함.
    - private method 의 단위테스트는 어떻게 처리할지
    - profiler에서 integration test 까지 추가하려 했으나 시간이 모자람.
    - 테스트를 위한 유틸 및 base 클래스들은 구조를 좀 더 이쁘게 가져갔어야 했다
- Kafka
    - 카프카에서 transaction 처리 과정을 좀 더 자세히 공부해봐야겠다
    - produce/consume 에서 예외 발생시 로직 처리를 더 보강하면 좋을것 같다. (전체적으로 error handling 은 아쉽다) 
    - 다른 부족한 부분은 뭐가 있었을까...?
- 설계
    - 모델 설계 및 클래스명을 도중에 몇번 바꾸었는데... 개발하면서도 계속 찝찝. 다음부터는 처음에 확실하게 정의해보자.
    - core 모듈에는 어느정도의 의존성을?
        - 처음에 service 를 넣지 않으려고 했으나, kafka consumer 때문에 공통(?)의 인터페이스 정도는 넣어보자 생각 -> 통일성이 없어보이는데?
    - 추상화 & clean code & oop ....
    - Repository 에서 Account 와 Customer의 인터페이스를 분리해놓았음. 그러나 실제 엔티티는 하나라서.. 분리하는게 맞았는지 모르겠음. (겉보기를 의식한듯)
- maven
    - 가장 나중에 빌드 & 디펜던시 & 패키징 처리를 했는데.. maven을 잘 써보지 않아 시간이 모자랄뻔 했음. (사소하게 생각함)
    - 다음에는 처음 프로젝트 템플릿을 잡을때 어느정도 구성을 해보는게 좋을것 같다.