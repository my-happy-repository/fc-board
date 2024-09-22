## Board Project 입니다.

## Spring 실행 시
### java.lang.NullPointerException: Cannot invoke "org.hibernate.engine.jdbc.spi.SqlExceptionHelper.convert(java.sql.SQLException, String)" because the return value of "org.hibernate.resource.transaction.backend.jdbc.internal.JdbcIsolationDelegate.sqlExceptionHelper()" is null
### 해당 에러 해결이 필요 !

## AWS ElasticBeanstalk 을 구성 !

## RDS 생성 후
## CREATE DATABASE BOARD; BOARD 데이터 베시으 생성 후 Schema 를 조회 SHOW SCHEMAS;

## 요구 사항
### 문제를 해결하기 위해 필요한 기능
### 요구 사항 분석이란 ?
#### 요구 사항을 이해하는 과정
#### 유스케이스를 작성하는 것이 요구 사항을 이해하는데 도움이 됨

### Use-Case
#### 사용자 중심으로 어떠한 기능을 수행하였을 때 발생하는 겅공 / 실패 시나리오
#### 유스케이스 작성이란 ? 요구 사항을 분석하여 사용자 중심 시나리오를 풀어내는 것

### 왜 Nginx 는 port 를 5000 번으로 설정하여야 할까 ... ?! 스터디 공부가 필요 ..> !!!

### 게시글 구성
#### 게시글 ID, 제목, 내용, 작성 시간, 작성자, 수정 시간, 수정 자

### 게시글 태그 생성

### 성능 최적화
#### 작업을 빠르게 처리하기 보다는 -> 사용자 경험 개선이 우선
### 성능 최적화 방법
#### 1. 애플리케이션 코드의 비효율적인 부분을 개선
#### 하지 않아도 될 연산, 중복 수행하는 연산, 불 필요한 객체 생성, N+1
### 2. 데이터 베이스
#### 이해하기 힘든 쿼리 (서브, 중첩 쿼리), 비 효율적인 테이블 구조, 풀 테이블 스캔
### 3. Caching
#### Local Caching (EHCaching ....) / Global Caching (ex. Redis ...)
### 4. Event
#### Spring Event, Kafka, Rabbit MQ, SQS (Event 사용 이유 서비스 간의 의존성을 없애주고, 비 동기 처리 등 .. 에 사용)
### 5. 그 외
#### 동시성 처리 -> Thread, CompletableFuture, Coroutine, 스케일링, JVM (GC) 튜닝

### N+1 문제 -> 특정 엔티티를 DB 에서 조회할 때 나가는 1번의 쿼리 + 연관 객체를 조회하는 N 번의 예상치 못한 쿼리

### Database Index
#### 책의 색인 (페이지 주소) : 책의 내용, 인덱스 (데이터 주소) : 데이터
#### 장점 : 검색 속도를 증가 시킴, 단점 : Insert / Update / Delete 가 느려짐, 인덱스의 정렬 된 순서를 유지 시키는 비용

#### 인덱스 생성 방식
#### 1. 쿼리 실행 계획을 확인, 2. 인덱스 추가, 3 쿼리 실행 계획 변경 확인

### INDEX 를 생성하기 전에는 해당 쿼리는 type 이 ALL 이 였음
`EXPLAIN
SELECT count(l1_0.id)
FROM likes l1_0
WHERE l1_0.post_id = 1;`

### INDEX 를 생성 후 type 이 ref 로 변경이 됨
`CREATE INDEX idx_post_id on likes(post_id);`

### Spring Application 에서 이벤트를 발행/구독 할 수 있는 기능
#### ApplicationEventPublisher / EventListener
### 이벤트를 사용하는 이유
#### 1. 비동기 처리 (빠른 응답), 2. 의존성 줄이기 3. 데이터 싱크 맞추기











