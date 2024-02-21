# 인프런 워밍업 클럽 0기 - 백엔드 코스

과제 제출

| 과제     |링크|
|--------|---|
| 과제1    |[링크](https://beomseok37.tistory.com/181)|
| 과제2    |[링크](https://www.inflearn.com/blogs/6553)|
| 과제3    |[링크](https://www.inflearn.com/blogs/6633)|
| 과제4    |[]()|
| 과제5    |[]()|
| 과제6    |[]()|
| 과제7    |[]()|
| 미니프로젝트 |[]()|

## Day 1

@SpringBootApplication

- 스프링 실행에 필요한 설정을 자동으로 해준다.

```java
SpringApplication.run(LibraryAppApplication.class, qrgs);
```

- 스프링 어플리케이션 실행 명령어

HTTP & API

- 컴퓨터 간의 통신은 HTTP라는 표준화된 형식이 있다.
- HTTP 요청의 구성요소로는 method와 path가 있다.
- 데이터 요청, 응답에 사용되는 방법은 쿼리와 바디이다.
- HTTP 응답은 상태코드를 가지고 있다.
- 클라이언트와 서버가 HTTP 통신을 통해 정해진 기능을 수행하도록 하는 것을 API라고 한다.

기본 API 구현

```java
@RestController
public class SampleController {
    
    @GetMapping("/")
    public String hello(){
        return "hello";
    }
}
```

Path, Query

- HTTP 통신 시 URL에 데이터를 담는 방식
- Path Variable: {HOST_URL}/users/{**user_id**}
  - 특정 데이터의 리소스를 원할 경우
  - 예) 123번 유저의 정보를 얻어올 경우
- Query Parameter: {HOST_URL}/users?**age=15**
  - 여러 데이터의 정렬이나 필터링이 필요할 경우
  - 예) 나이가 20살인 유저들 정보를 얻어올 경우

---