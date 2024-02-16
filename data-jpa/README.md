## 섹션 3

공통 인터페이스 설정

- 스프링 부트 사용시 @SpringBootApplication에서 위치 지정(따로 설정할 필요 없음)
- 만약 위치가 달라진다면 @EnableJpaRepository로 스캔할 위치를 지정해주어야 한다.
- 인터페이스 작성 시 Spring Data JPA가 어플리케이션 로딩 시점에 구현체를 만들어준다. (프록시)
- JpaRepository 인터페이스 상속 시 @Repository 어노테이션을 설정하지 않아도 자동으로 컴포넌트 스캔이 되도록 한다.
  - JPA 예외를 스프링 예외로 변환하는 과정도 자동으로 처리
- 주요 메서드
  - save(S)
  - delete(T)
  - findById(ID)
  - getOne(ID) : 엔티티를 프록시로 조회
  - findAll()